package sample.compiler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompilerService {

    @Autowired
    private DefaultListableBeanFactory beanFactory;

    public void register(String className, Class<?> clazz) {

        // remove
        if (beanFactory.containsBean(className)) {
            beanFactory.removeBeanDefinition(className);
        }

        // register
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanFactory.registerBeanDefinition(className, beanDefinition);
    }

    public Class<?> compiler(String className, String sourceCode, CustomizerClassLoader classLoader) {

        // 获取java的编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // 存放编译过程中输出的信息
        DiagnosticCollector<JavaFileObject> listener = new DiagnosticCollector<>();

        // 标准的内容管理器,更换成自己的实现，覆盖部分方法
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(listener, null, null);
        CustomizerJavaFileManage customizerJavaFileManage = new CustomizerJavaFileManage(standardFileManager);

        // 构造源代码对象
        CustomizerJavaFileObject customizerJavaFileObject = new CustomizerJavaFileObject(className, sourceCode);

        // 获取一个编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, customizerJavaFileManage, listener, null, null, Arrays.asList(customizerJavaFileObject));
        // 编译
        task.call();

        // 获取编译错误信息
        List<Diagnostic<? extends JavaFileObject>> diagnostics = listener.getDiagnostics();
        if (diagnostics != null && !diagnostics.isEmpty()) {
            StringBuilder buffer = new StringBuilder();
            for (Diagnostic diagnostic : diagnostics) {
                buffer.append(diagnostic.toString()).append("\n");
            }
            throw new RuntimeException(buffer.toString());
        }

        // 获取编译后的字节码
        byte[] bytes = customizerJavaFileObject.getBytes();
        if (bytes == null || bytes.length == 0) {
            throw new RuntimeException("can not found compile bytes");
        }

        // 获取class
        return classLoader.loadClass(className, bytes);
    }

    private class CustomizerJavaFileObject extends SimpleJavaFileObject {

        private String sourceCodes;
        private ByteArrayOutputStream outPutStream;

        private CustomizerJavaFileObject(String className, String sourceCodes) {
            super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
            this.sourceCodes = sourceCodes;
            this.outPutStream = new ByteArrayOutputStream();
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return sourceCodes;
        }

        @Override
        public OutputStream openOutputStream() {
            return outPutStream;
        }

        private byte[] getBytes() {
            return outPutStream.toByteArray();
        }

    }

    private class CustomizerJavaFileManage extends ForwardingJavaFileManager {

        private CustomizerJavaFileManage(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
            return (JavaFileObject) sibling;
        }

    }

    public static class CustomizerClassLoader extends ClassLoader {

        private final Map<String, Class<?>> MAP;

        public CustomizerClassLoader() {
            MAP = new HashMap<String, Class<?>>();
        }

        private Class<?> loadClass(String className, byte[] bytes) {
            Class<?> clazz = defineClass(className, bytes, 0, bytes.length);
            MAP.put(className, clazz);
            return clazz;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            Class<?> clazz = MAP.get(name);
            if (clazz != null) {
                return clazz;
            }

            if (name.endsWith("Customizer")) {
                clazz = MAP.get(name.substring(0, name.length() - "Customizer".length()));
            }

            if (clazz == null) {
                throw new ClassNotFoundException("can not found class " + name);
            }

            return clazz;
        }

    }

}
