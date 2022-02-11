package sample.compiler.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomCompilerUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomCompilerUtilsTest.class);

    @Autowired
    private CustomBeanFactory customBeanFactory;

    @Test
    public void compile() throws Exception {
        String className = "sample.compiler.file.FileService";
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);

        // 文件内容
        String contents = this.content(simpleClassName + ".java");

        // bean名称
        String beanName = StringUtils.uncapitalize(simpleClassName);

        // class loader
        CustomClassLoader customClassLoader = new CustomClassLoader();

        // 编译
        Class<?> clazz = CustomCompilerUtils.compile(customClassLoader, className, contents);

        // 注册
        customBeanFactory.register(beanName, clazz);

        // 调用
        Object bean = customBeanFactory.getBean(beanName);
        Method method = clazz.getMethod("show", new Class[]{String.class});
        Object result = method.invoke(bean, new Object[]{"jack"});
        logger.info("result: {}", result);
    }

    @Test
    public void compileMoreVersion() throws Exception {
        String className = "sample.compiler.file.FileService";
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);

        // 文件内容
        String contents = this.content(simpleClassName + ".java");

        // bean名称
        String beanName = StringUtils.uncapitalize(simpleClassName);

        // class loader
        CustomClassLoader customClassLoader = new CustomClassLoader();

        // 编译
        Class<?> clazz = CustomCompilerUtils.compile(customClassLoader, className, contents);

        // 注册
        customBeanFactory.register(beanName, clazz);

        // 调用
        Object bean = customBeanFactory.getBean(beanName);
        Method method = clazz.getMethod("show", new Class[]{String.class});
        Object result = method.invoke(bean, new Object[]{"jack"});
        logger.info("result: {}", result);


        // ========================= 代码变更 =========================
        contents = this.content(simpleClassName + "2.java");
        // class loader[需要新的class loader]
        CustomClassLoader customClassLoader2 = new CustomClassLoader();
        // 编译
        clazz = CustomCompilerUtils.compile(customClassLoader2, className, contents);
        // 注册
        customBeanFactory.register(beanName, clazz);

        // 调用
        bean = customBeanFactory.getBean(beanName);
        method = clazz.getMethod("show", new Class[]{String.class});
        result = method.invoke(bean, new Object[]{"jack"});
        logger.info("result: {}", result);
    }

    @Test
    public void compileMoreClass() throws Exception {
        String className = "sample.compiler.file.FileService";
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);

        // 文件内容
        String contents = this.content(simpleClassName + ".java");

        // bean名称
        String beanName = StringUtils.uncapitalize(simpleClassName);

        // class loader
        CustomClassLoader customClassLoader = new CustomClassLoader();

        // 编译
        Class<?> clazz = CustomCompilerUtils.compile(customClassLoader, className, contents);

        // 注册
        customBeanFactory.register(beanName, clazz);

        // 调用
        Object bean = customBeanFactory.getBean(beanName);
        Method method = clazz.getMethod("show", new Class[]{String.class});
        Object result = method.invoke(bean, new Object[]{"jack"});
        logger.info("result: {}", result);


        // ========================= 不同的class =========================
        className = "sample.compiler.file.ContentService";
        simpleClassName = className.substring(className.lastIndexOf(".") + 1);
        contents = this.content(simpleClassName + ".java");
        // 编译
        clazz = CustomCompilerUtils.compile(customClassLoader, className, contents); // 使用同一个class loader
        // 注册
        customBeanFactory.register(beanName, clazz);

        // 调用
        bean = customBeanFactory.getBean(beanName);
        method = clazz.getMethod("content", new Class[]{String.class});
        result = method.invoke(bean, new Object[]{"jack"});
        logger.info("result: {}", result);
    }

    @Test
    public void compileAndListen() throws Exception {
        String className = "sample.compiler.file.FileService";
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);

        // 文件内容
        String contents = this.content(simpleClassName + ".java");

        // bean名称
        String beanName = StringUtils.uncapitalize(simpleClassName);

        // class loader
        CustomClassLoader customClassLoader = new CustomClassLoader();

        // 编译
        Class<?> clazz = CustomCompilerUtils.compile(customClassLoader, className, contents);

        // 监听器
        CustomBeanFactory.Listener listener = (bean, beanName1, clazz1) -> logger.info("reload bean {}", beanName1);
        customBeanFactory.addListener(listener);

        // 注册
        customBeanFactory.register(beanName, clazz);

        // 调用
        Object bean = customBeanFactory.getBean(beanName);
        Method method = clazz.getMethod("show", new Class[]{String.class});
        Object result = method.invoke(bean, new Object[]{"jack"});
        logger.info("result: {}", result);
    }

    private String content(String relativeFilename) {


        String path = CustomCompilerUtilsTest.class.getClassLoader().getResource(".").getPath();
        path = path.substring(0, path.lastIndexOf("/target"));

        File file = new File(new File(path, "src/test/resources/compiler"), relativeFilename);

        InputStream is = null;
        ByteArrayOutputStream os = null;

        try {

            is = new FileInputStream(file);
            os = new ByteArrayOutputStream();

            StreamUtils.copy(is, os);

            return os.toString("UTF-8");
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.warn("", e);
                }
            }

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.warn("", e);
                }
            }

        }

        return null;
    }

}