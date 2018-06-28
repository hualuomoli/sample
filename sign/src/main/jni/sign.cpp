//
// Created by admin on 2018/6/25.
//

#include "android/log.h"
#include "sample_jni_sign_Sign.h"

#define TAG    "sign"
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)

// 获取上下文
jobject getContext(JNIEnv *env) {
    //获取Activity Thread的实例对象
    jclass activityThreadClass = env->FindClass("android/app/ActivityThread");
    jmethodID currentActivityThreadMethodID = env->GetStaticMethodID(activityThreadClass, "currentActivityThread", "()Landroid/app/ActivityThread;");
    jobject currentActivityThread = env->CallStaticObjectMethod(activityThreadClass, currentActivityThreadMethodID);
    //获取Application，也就是全局的Context
    jmethodID getApplicationMethodID = env->GetMethodID(activityThreadClass, "getApplication", "()Landroid/app/Application;");
    return env->CallObjectMethod(currentActivityThread, getApplicationMethodID);
}

// 获取包名
jstring getPackageName(JNIEnv *env, jobject context) {
    jclass contextClass = env->FindClass("android/content/Context");
    jmethodID getPackageNameMethodID = env->GetMethodID(contextClass, "getPackageName", "()Ljava/lang/String;");
    jstring packageName = (jstring)env->CallObjectMethod(context, getPackageNameMethodID);

    return packageName;
}


// 获取签名
jstring getSign(JNIEnv *env, jclass clazz) {
    // 1、获取上下文
    jobject context = getContext(env);

    // 获取flag
    jfieldID flagFieldID = env->GetStaticFieldID(clazz, "FLAG", "I");
    jint flag = env->GetStaticIntField(clazz, flagFieldID);

    // 2、PackageManager(context.getPackageManager())
    jclass contextClass = env->GetObjectClass(context);
    jmethodID getPackageManagerMethodID = env->GetMethodID(contextClass, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject packageManager = env->CallObjectMethod(context, getPackageManagerMethodID);

    // 3、packageName(context.getPackageName())
    jmethodID getPackageNameMethodID = env->GetMethodID(contextClass, "getPackageName", "()Ljava/lang/String;");
    jstring packageName = (jstring)env->CallObjectMethod(context, getPackageNameMethodID);

    // 3、PackageInfo(PackageManager.getPackageInfo(Sting, int))
    jclass packageManagerClass = env->GetObjectClass(packageManager);
    jmethodID getPackageInfoMethodID = env->GetMethodID(packageManagerClass, "getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jobject packageInfo = env->CallObjectMethod(packageManager, getPackageInfoMethodID, packageName, flag);


    // 4、signature(PackageInfo.signatures[0])
    jclass packageInfoClass = env->GetObjectClass(packageInfo);
    jfieldID signaturesFieldID = env->GetFieldID(packageInfoClass, "signatures", "[Landroid/content/pm/Signature;");
    jobjectArray signatures = (jobjectArray)env->GetObjectField(packageInfo, signaturesFieldID);
    jobject signature = env->GetObjectArrayElement(signatures, 0);

    // 5、Signature.toCharsString()
    jclass signatureClass = env->GetObjectClass(signature);
    jmethodID toCharsStringMethodID = env->GetMethodID(signatureClass, "toCharsString", "()Ljava/lang/String;");
    jstring signatureString = (jstring)env->CallObjectMethod(signature, toCharsStringMethodID);

    return signatureString;
}

// 是否认证成功
jboolean checkSuccess(JNIEnv *env, jclass  clazz) {

    // 是否已经初始化
    jfieldID initFieldID = env->GetStaticFieldID(clazz, "init", "Z");
    jboolean init = env->GetStaticBooleanField(clazz, initFieldID);

    jfieldID successFieldID = env->GetStaticFieldID(clazz, "success", "Z");

    // 已经初始化
    if(init) {
        // 判断是否认证成功
        return env->GetStaticBooleanField(clazz, successFieldID);
    }

    LOGI("未初始化,验证是否匹配");

    // 1、从APP中获取上下文
    jstring runtimeSign = getSign(env, clazz);
    jstring sign = env->NewStringUTF("3082037b30820263a003020102020468568a89300d06092a864886f70d01010b0500306e310b300906035504061302434e3111300f060355040813087368616e646f6e673110300e0603550407130771696e6764616f31133011060355040a130a636f6d2e67697468756231133011060355040b130a636f6d2e6769746875623110300e06035504031307616e64726f6964301e170d3138303430373032303535395a170d3433303430313032303535395a306e310b300906035504061302434e3111300f060355040813087368616e646f6e673110300e0603550407130771696e6764616f31133011060355040a130a636f6d2e67697468756231133011060355040b130a636f6d2e6769746875623110300e06035504031307616e64726f696430820122300d06092a864886f70d01010105000382010f003082010a0282010100ac8e3b9ccebed3ee76a5af59fec8925cc04c8f56585521bcdbe3b6f6cbacd5b4a2f34c3fb0567569225500b10577e11369d342b3df98c2386b520898445335f865897d3bf51a8b2aab0e66fa1dbaf897274e0bed479f4c351239e9f04945d4e3e74752ba657aea06a33802c155090846ed2b5086b56efcf22187fabdd771444a4fd624786c223b1c79882ba66547697ab19a03e45d3661de1a335475a3198532c05c66a08be81cb0f6c568f20dc6f7a3b461ba8493d05d3cfd37748d37416e3ec9b632b1e5ed301ed77f9004e335cb89e1de44c4b33cd12e24aa527e5fceb2a27ef43f70d9e58c1dd11880d167ca26078050ed01d1769fecd0ca61d57e3470290203010001a321301f301d0603551d0e041604146f22cd9bdd8e5972445ac11b195386c1e9c4c42d300d06092a864886f70d01010b05000382010100015502a2d3c7d4421c7a0403db99dc48732db4d96c6c340321a6466a1d7f15a9b72338d7d1a0e517a7d87d8489eee82ae46afdd326671be26b126b5f89899102f162d37f1db7ac393d8e8e29363900e4fe96b2ba805ef1c24703ba0eb8472ae7e87f40e778cdc1afaa4bdfe7d505b9cdc5eb975840ca076e2b4885392fa2769bbc5a0eb160aeff1ace010310e2f59d3e86f2cb7091660654263984b9dbba94bd294db49ab6f17420eafaaaf5aad3ffcf8ae3bbce15a594b6bdf5580787caf3b470f26c82aa965b39ce796eb04cf625a77f50ec0cbfd251a015af179a84fc9a245a2e6449ba187362b5fd14a494762611288df39bb4822a36fe5e7cfe00697d82");
    // check

    // String.equals(Object)
    jclass stringClass = env->FindClass("java/lang/String");
    jmethodID equalsMethodID = env->GetMethodID(stringClass, "equals", "(Ljava/lang/Object;)Z");
    jboolean success = env->CallBooleanMethod(sign, equalsMethodID, runtimeSign);

    // 设置已初始化
    env->SetStaticBooleanField(clazz, initFieldID, 1);
    // 设置认证结果
    env->SetStaticBooleanField(clazz, successFieldID, success);

    return success;

}

JNIEXPORT jobject JNICALL Java_sample_jni_sign_Sign_getContext(JNIEnv *env, jclass clazz) {
    jobject context = getContext(env);
    return context;
}

JNIEXPORT jstring JNICALL Java_sample_jni_sign_Sign_getSign(JNIEnv *env, jclass clazz) {
    return getSign(env, clazz);
}

JNIEXPORT jstring JNICALL Java_sample_jni_sign_Sign_say(JNIEnv *env, jclass clazz, jstring world) {
    jboolean success = checkSuccess(env, clazz);
    if(!success) {
        jclass exceptionClass = env->FindClass("java/lang/Exception");
        env->ThrowNew(exceptionClass, "认证不通过");
        return NULL;
    }

    jclass stringBuilderClass = env->FindClass("java/lang/StringBuilder");
    jmethodID stringBuilderContructMethodID = env->GetMethodID(stringBuilderClass, "<init>", "()V");
    jobject stringBuilder = env->NewObject(stringBuilderClass, stringBuilderContructMethodID);

    jmethodID appendMethodID = env->GetMethodID(stringBuilderClass, "append", "(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;");
    env->CallObjectMethod(stringBuilder, appendMethodID, env->NewStringUTF("I can say "));
    env->CallObjectMethod(stringBuilder, appendMethodID, world);
    env->CallObjectMethod(stringBuilder, appendMethodID, env->NewStringUTF(" in c."));

    jmethodID toStringMethodID = env->GetMethodID(stringBuilderClass, "toString", "()Ljava/lang/String;");
    jstring result = (jstring)env->CallObjectMethod(stringBuilder, toStringMethodID);

    return result;
}