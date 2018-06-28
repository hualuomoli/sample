//
// Created by admin on 2018/6/20.
//

#include "android/log.h"
#include "sample_jni_logger_Logger.h"

JNIEXPORT void JNICALL Java_sample_jni_logger_Logger_verbose(JNIEnv *env, jclass clazz) {
    __android_log_print(ANDROID_LOG_VERBOSE, "logger", "verbose print data %s", "jni");
}

JNIEXPORT void JNICALL Java_sample_jni_logger_Logger_debug(JNIEnv *env, jclass clazz) {
    __android_log_print(ANDROID_LOG_DEBUG, "logger", "debug print data %s", "c");
}

JNIEXPORT void JNICALL Java_sample_jni_logger_Logger_info(JNIEnv *env, jclass clazz) {
    __android_log_print(ANDROID_LOG_INFO, "logger", "info print data %d", 55);
}


JNIEXPORT void JNICALL Java_sample_jni_logger_Logger_warn(JNIEnv *env, jclass clazz) {
    __android_log_print(ANDROID_LOG_WARN, "logger", "warn print data %f", 6.66f);
}


JNIEXPORT void JNICALL Java_sample_jni_logger_Logger_error(JNIEnv *env, jclass clazz) {
    __android_log_print(ANDROID_LOG_ERROR, "logger", "error print data %s", "Exception");
}