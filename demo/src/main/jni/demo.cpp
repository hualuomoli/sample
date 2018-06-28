//
// Created by admin on 2018/6/20.
//

#include "sample_jni_demo_Demo.h"

JNIEXPORT jstring JNICALL Java_sample_jni_demo_Demo_say(JNIEnv *env, jclass clazz, jstring value) {

    return env->NewStringUTF("from c");
}