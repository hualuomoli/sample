package sample.compiler.util;

import java.util.HashMap;
import java.util.Map;

public class CustomClassLoader extends ClassLoader {

    private final Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

    public Class<?> defineClass(String className, byte[] bytes) {
        Class<?> clazz = defineClass(className, bytes, 0, bytes.length);
        classMap.put(className, clazz);
        return clazz;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String className = null;
        if (name.endsWith("Customizer")) {
            className = name.substring(0, name.length() - "Customizer".length());
        } else {
            className = name;
        }

        Class<?> clazz = classMap.get(className);
        if (clazz == null) {
            throw new ClassNotFoundException("can not found class " + className);
        }

        return clazz;
    }

}
