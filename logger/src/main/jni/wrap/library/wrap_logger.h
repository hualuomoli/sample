//
// Created by admin on 2018/6/21.
//
#include "android/log.h"

#ifndef JNI_WRAP_LOGGER_H
#define JNI_WRAP_LOGGER_H

#endif //JNI_WRAP_LOGGER_H

// 定义是否输出
#define PRINT_LOG true
// 定义是否使用NDK输出
#define NDK_LOG true

#if PRINT_LOG
    #if PRINT_LOG
        #define log_print_verbose(tag, ...) __android_log_print(ANDROID_LOG_VERBOSE, tag, __VA_ARGS__)
        #define log_print_debug(tag, ...) __android_log_print(ANDROID_LOG_DEBUG, tag, __VA_ARGS__)
        #define log_print_info(tag, ...) __android_log_print(ANDROID_LOG_INFO, tag, __VA_ARGS__)
        #define log_print_warn(tag, ...) __android_log_print(ANDROID_LOG_WARN, tag, __VA_ARGS__)
        #define log_print_error(tag, ...) __android_log_print(ANDROID_LOG_ERROR, tag, __VA_ARGS__)
    #else
        #define log_print_verbose(tag, ...)  printf(tag, __VA_ARGS__)
        #define log_print_debug(tag, ...) printf(tag, __VA_ARGS__)
        #define log_print_info(tag, ...) printf(tag, __VA_ARGS__)
        #define log_print_warn(tag, ...)  printf(tag,__VA_ARGS__)
        #define log_print_error(tag, ...) printf(tag,  __VA_ARGS__)
     #endif
#else
    #define log_print_verbose(tag...)
    #define log_print_debug(tag...)
    #define log_print_info(tag...)
    #define log_print_warn(tag...)
    #define log_print_error(tag...)
#endif

