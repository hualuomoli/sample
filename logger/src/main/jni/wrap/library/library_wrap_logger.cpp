//
// Created by admin on 2018/6/20.
//

//
// Created by admin on 2018/6/20.
//

#include "android/log.h"
#include "wrap_logger.h"
#include "sample_jni_logger_LibraryWrapLogger.h"

// 这个是自定义的LOG的标识
#define TAG "library wrap logger"

// 定义实际使用的方法
#define LOGV(...) log_print_verbose(TAG, __VA_ARGS__)
#define LOGD(...) log_print_debug(TAG,__VA_ARGS__)
#define LOGI(...) log_print_info(TAG,__VA_ARGS__)
#define LOGW(...) log_print_warn(TAG,__VA_ARGS__)
#define LOGE(...) log_print_error(TAG,__VA_ARGS__)

JNIEXPORT void JNICALL Java_sample_jni_logger_LibraryWrapLogger_verbose(JNIEnv *env, jclass clazz) {
    LOGV("verbose print data %s", "c");
}

JNIEXPORT void JNICALL Java_sample_jni_logger_LibraryWrapLogger_debug(JNIEnv *env, jclass clazz) {
    LOGD("debug print data %s", "c");
}

JNIEXPORT void JNICALL Java_sample_jni_logger_LibraryWrapLogger_info(JNIEnv *env, jclass clazz) {
    LOGI("info print data %d", 55);
}


JNIEXPORT void JNICALL Java_sample_jni_logger_LibraryWrapLogger_warn(JNIEnv *env, jclass clazz) {
    LOGW("warn print data %f", 6.66f);
}


JNIEXPORT void JNICALL Java_sample_jni_logger_LibraryWrapLogger_error(JNIEnv *env, jclass clazz) {
    LOGE("warn print data %s", "Exception");
}