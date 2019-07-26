package sample.flink.compiler;

import java.io.IOException;
import java.util.Map;

import com.itranswarp.compiler.JavaStringCompiler;

/**
 * java源文件编译
 */
public class JavaOriginCompiler {

    private static final JavaStringCompiler compiler = new JavaStringCompiler();

    public static Class<?> load(String className, String source) {
        try {
            return compile(className, source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> compile(String className, String source) throws IOException, ClassNotFoundException {
        String classSimpleName = className.substring(className.lastIndexOf(".") + 1) + ".java";
        Map<String, byte[]> results = compiler.compile(classSimpleName, source);
        return compiler.loadClass(className, results);
    }

}
