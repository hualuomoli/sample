package sample.flink.compiler;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaOriginCompilerTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaOriginCompiler.class);
    private static ClassLoader classLoader = null;

    @BeforeClass
    public static void beforeClass() {
        classLoader = JavaOriginCompilerTest.class.getClassLoader();
    }

    @Test
    @Ignore
    public void testLoad() throws Exception {
        String source = this.readFile("source/origin.txt");
        Class<?> clazz = JavaOriginCompiler.load("sample.flink.compiler.Demo", source);
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("say", new Class[] { String.class });
        Object result = method.invoke(obj, new Object[] { "compiler" });
        logger.info("result:{}", result);
    }

    @Test
    @Ignore
    public void testReload() throws Exception {
        String className = "sample.flink.compiler.Demo";

        String source = this.readFile("source/origin.txt");
        Class<?> clazz = JavaOriginCompiler.load(className, source);
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("say", new Class[] { String.class });
        Object result = method.invoke(obj, new Object[] { "compiler" });
        logger.info("result:{}", result);

        // reload
        source = this.readFile("source/update.txt");
        clazz = JavaOriginCompiler.load(className, source);
        // instance new object
        obj = clazz.newInstance();
        method = clazz.getMethod("say", new Class[] { String.class });
        result = method.invoke(obj, new Object[] { "compiler" });
        logger.info("update result:{}", result);

    }

    @SuppressWarnings("unchecked")
    private String readFile(String path) throws Exception {
        InputStream is = classLoader.getResourceAsStream(path);
        List<String> lines = IOUtils.readLines(is);
        StringBuilder buffer = new StringBuilder();
        for (String line : lines) {
            buffer.append(line).append("\n");
        }
        is.close();

        return buffer.toString();
    }

}
