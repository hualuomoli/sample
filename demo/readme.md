# 创建native方法
```
public static native String say(String world);
```

# 生成.h文件
+ 打开Terminal
+ 进入java目录 <br/>
`cd demo/src/main/java`
+ 使用javah生成.h文件<br/>
`javah -d ../jni -encoding UTF-8 sample.jni.demo.Demo` <br/>
参数说明
```
     -d          生成文件目录
     -encoding   文件编码
```

# 编写.cpp文件
`demo/src/main/jni/demo.cpp`

```
#include "sample_jni_demo_Demo.h"

JNIEXPORT jstring JNICALL Java_sample_jni_demo_Demo_say(JNIEnv *env, jclass clazz, jstring value) {

    return env->NewStringUTF("from c");
}
```

# 配置CMakeLists.txt
```
# cmake版本号
cmake_minimum_required(VERSION 3.4.1)

# 添加依赖库
add_library( # 库的名称
             demo

             # 这里就用这个，我也不知道.
             SHARED

             # 依赖的cpp文件
             src/main/jni/demo.cpp )

# 目标链
target_link_libraries( # 目标链库名称
                       demo

                       # 依赖库
                       ${log-lib} )
```

# 配置build.gradle
在android下增加
```
    // jni
    externalNativeBuild {
        cmake {
            path "CMakeLists.txt"
        }
    }
```

# Demo类 加载so
```
  static {
    System.loadLibrary("demo");
  }
```
