//
// Created by admin on 2018/6/20.
//

//
// Created by admin on 2018/6/20.
//

#include "android/log.h"
#include "sample_jni_logger_SimpleWrapLogger.h"

// 这个是自定义的LOG的标识
#define TAG    "simple wrap logger"

// 定义LOG方法
#define LOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE,TAG,__VA_ARGS__)
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)

JNIEXPORT void JNICALL Java_sample_jni_logger_SimpleWrapLogger_verbose(JNIEnv *env, jclass clazz) {
    LOGV("verbose print data %s", "jni");
}

JNIEXPORT void JNICALL Java_sample_jni_logger_SimpleWrapLogger_debug(JNIEnv *env, jclass clazz) {
    LOGD("debug print data %s", "c");
}

JNIEXPORT void JNICALL Java_sample_jni_logger_SimpleWrapLogger_info(JNIEnv *env, jclass clazz) {
    LOGI("info print data %d", 55);
}


JNIEXPORT void JNICALL Java_sample_jni_logger_SimpleWrapLogger_warn(JNIEnv *env, jclass clazz) {
    LOGW("warn print data %f", 6.66f);
}


JNIEXPORT void JNICALL Java_sample_jni_logger_SimpleWrapLogger_error(JNIEnv *env, jclass clazz) {
    LOGE("error print data %s", "Exception");
}