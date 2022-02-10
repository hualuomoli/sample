package sample.compiler.util;

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
import java.util.List;

public class CustomCompilerUtils {

    public static Class<?> compile(CustomClassLoader customClassLoader, String className, String sourceCode) {

        // 获取java的编译器
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        // 存放编译过程中输出的信息
        DiagnosticCollector<JavaFileObject> diagnosticListener = new DiagnosticCollector<>();

        // 标准的内容管理器,更换成自己的实现，覆盖部分方法
        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(diagnosticListener, null, null);
        CustomJavaFileManage customizerJavaFileManage = new CustomJavaFileManage(standardFileManager);

        // 构造源代码对象
        CustomJavaFileObject customJavaFileObject = new CustomJavaFileObject(className, sourceCode);

        // 获取一个编译任务
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, customizerJavaFileManage, diagnosticListener, null, null, Arrays.asList(customJavaFileObject));
        // 编译
        task.call();

        // 获取编译错误信息
        List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticListener.getDiagnostics();
        if (diagnostics != null && !diagnostics.isEmpty()) {
            StringBuilder buffer = new StringBuilder();
            for (Diagnostic diagnostic : diagnostics) {
                buffer.append(diagnostic.toString()).append("\n");
            }
            throw new RuntimeException(buffer.toString());
        }

        // 获取编译后的字节码
        byte[] bytes = customJavaFileObject.getBytes();

        // 获取class
        return customClassLoader.defineClass(className, bytes);
    }

    private static class CustomJavaFileObject extends SimpleJavaFileObject {

        private String sourceCodes;
        private ByteArrayOutputStream outPutStream;

        CustomJavaFileObject(String className, String sourceCodes) {
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

    private static class CustomJavaFileManage extends ForwardingJavaFileManager {

        CustomJavaFileManage(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
            return (JavaFileObject) sibling;
        }

    }

}
