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