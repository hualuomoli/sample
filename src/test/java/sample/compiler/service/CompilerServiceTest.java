package sample.compiler.service;

import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CompilerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CompilerServiceTest.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private CompilerService compilerService;

    @Test
    public void compile() throws Exception {
        InputStream is = CompilerServiceTest.class.getResourceAsStream("/compiler/FileController.java");
        Validate.notNull(is);
        String contents = StreamUtils.copyToString(is, Charset.forName("UTF-8"));
        String className = "sample.compiler.file.FileController";

        // class loader(同一个classLoader重新编译会报错)
        CompilerService.CustomizerClassLoader classLoader = new CompilerService.CustomizerClassLoader();
        // 编译
        Class<?> clazz = compilerService.compiler(className, contents, classLoader);
        // 注册到spring
        compilerService.register(className, clazz);

        // 调用
        Object bean = context.getBean(clazz);
        Method method = clazz.getMethod("show", new Class[]{String.class});
        Object result = method.invoke(bean, new Object[]{"jack"});
        logger.info("result: {}", result);


    }

}