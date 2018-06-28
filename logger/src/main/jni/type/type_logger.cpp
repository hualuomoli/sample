//
// Created by admin on 2018/6/21.
//

#include "stdlib.h"
#include "string.h"
#include "android/log.h"
#include "sample_jni_logger_TypeLogger.h"

#define TAG    "type logger"
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)

char *jstringToChar(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("UTF-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

char *jobjectToChar(JNIEnv *env, jobject obj) {
    char *rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jmethodID valueOfMid = env->GetStaticMethodID(clsstring, "valueOf",
                                                  "(Ljava/lang/Object;)Ljava/lang/String;");
    jstring jstr = (jstring) env->CallStaticObjectMethod(clsstring, valueOfMid, obj);
    jstring strencode = env->NewStringUTF("UTF-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

JNIEXPORT void JNICALL Java_sample_jni_logger_TypeLogger_print(JNIEnv *env, jclass clazz
        // int
        , jint i
        // float
        , jfloat f
        // double
        , jdouble d
        // long
        , jlong l
        // boolean
        , jboolean b
        // byte
        , jbyte bt
        // char
        , jchar c

        // String
        , jstring s
        // Integer
        , jobject wi
        // Float
        , jobject wf
        // Double
        , jobject wd
        // Long
        , jobject wl
        // Boolean
        , jobject wb) {

    // %d 十进制有符号整数
    // %f 浮点数
    // %s 字符串
    // %c 单个字符
    // %p 指针的值
    // %e 指数形式的浮点数

    // 基本类型
    LOGI("parameter int value is %d", i);
    LOGI("parameter float value is %f", f);
    LOGI("parameter double value is %f", d);
    LOGI("parameter long value is %lld", l);
    // true=1, false=0
    LOGI("parameter boolean value is %d", b);
    LOGI("parameter byte value is %d", bt);
    LOGI("parameter char value is %d", c); // ASCII码 A=65

    // 字符串
    LOGI("parameter string value is %s", jstringToChar(env, s));

    // 包装类
    LOGI("parameter Integer value is %s", jobjectToChar(env, wi));
    LOGI("parameter Float value is %s", jobjectToChar(env, wf));
    LOGI("parameter Double value is %s", jobjectToChar(env, wd));
    LOGI("parameter Long value is %s", jobjectToChar(env, wl));
    LOGI("parameter Long Boolean is %s", jobjectToChar(env, wb));

}