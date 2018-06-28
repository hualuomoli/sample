//
// Created by admin on 2018/6/21.
//
#include "stdlib.h"
#include "string.h"
#include "android/log.h"
#include "sample_jni_api_Demo.h"

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

// java调用jni的静态方法
JNIEXPORT jstring JNICALL Java_sample_jni_api_Demo_call(
        // 指针
        JNIEnv *env
        // 当前类
        , jclass clazz) {

    // 1、获取静态方法
    jmethodID printStaticMethodID = env->GetStaticMethodID(clazz, "printStatic", "()V");

    // 2、打印属性值
    env->CallStaticVoidMethod(clazz, printStaticMethodID);

    // 3、获取属性
    jfieldID valueFieldID = env->GetStaticFieldID(clazz, "value", "Ljava/lang/String;");

    // 4、获取属性值
    jobject value = env->GetStaticObjectField(clazz, valueFieldID);
    LOGI("c print log, static value is %s", jobjectToChar(env, value));

    // 5、修改属性值
    jstring updateValue = env->NewStringUTF("修改后的值");
    env->SetStaticObjectField(clazz, valueFieldID, updateValue);

    // 6、打印属性值
    env->CallStaticVoidMethod(clazz, printStaticMethodID);

    // 7、释放内存
    env->DeleteLocalRef(value);
    env->DeleteLocalRef(updateValue);

    // 8、返回数据
    return env->NewStringUTF("c处理完成");
}

// java调用JNI的成员方法
JNIEXPORT void JNICALL Java_sample_jni_api_Demo_invoke(
        // 指针
        JNIEnv *env
        // this
        , jobject _this) {

    // 1、获取类
    jclass clazz = env->FindClass("sample/jni/api/Demo");

    // 2、获取方法
    jmethodID printMethodId = env->GetMethodID(clazz, "print", "()V");

    // 3、打印属性值
    env->CallVoidMethod(_this, printMethodId);

    // 4、获取属性
    jfieldID nameFieldID = env->GetFieldID(clazz, "name", "Ljava/lang/String;");

    // 5、获取属性值
    jobject name = env->GetObjectField(_this, nameFieldID);
    LOGI("c print log, name is %s", jobjectToChar(env, name));

    // 6、修改属性值
    jstring updateName = env->NewStringUTF("修改后的名称");
    env->SetObjectField(_this, nameFieldID, updateName);

    // 7、打印属性值
    env->CallVoidMethod(_this, printMethodId);

    // 8、释放内存
    env->DeleteLocalRef(name);
    env->DeleteLocalRef(updateName);

}

// C执行失败抛出异常
JNIEXPORT void JNICALL Java_sample_jni_api_Demo_error(JNIEnv *env, jclass clazz) {

    // 1、获取异常类
    jclass excepitonClazz = env->FindClass("java/lang/RuntimeException");

    // 直接抛出
    env->ThrowNew(excepitonClazz, "调用失败");

}