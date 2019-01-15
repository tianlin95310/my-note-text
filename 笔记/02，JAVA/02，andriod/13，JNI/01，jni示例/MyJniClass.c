////
//// Created by Administrator on 2017/5/11.
////
//#include <jni.h>
//#include <stdlib.h>
//#include <string.h>
//#include "tl_com_testmaterialdesign_navigation9_MyJniClass.h"
//
//#include <android/log.h>
//
//#define LOG_TAG "my"
//#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
//#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
//#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
//
//char *_JString2CStr(JNIEnv * env, jstring jstr)
//{
//    char *rtn = NULL;
//
//    // jclass对象
//    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
//
//    jstring strencode = (*env)->NewStringUTF(env, "GB2312");
//    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
//    jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid,
//                                                           strencode); // String .getByte("GB2312");
//    jsize alen = (*env)->GetArrayLength(env, barr);
//    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
//    if(alen > 0)
//    {
//        rtn = (char *) malloc(alen + 1); //"\0"
//        memcpy(rtn, ba, alen);
//        rtn[alen] = 0;
//    }
//    (*env)->ReleaseByteArrayElements(env, barr, ba, 0);
//    return
//    rtn;
//}
//
//// jni方法名 JNICALL Java_tl_com_testmaterialdesign_navigation9_MyJniClass_sum
//// jint 放回类型，他与c语言的数据类型相对应
//// JNIEnv 可以调用里面的很多方法
//// obj 谁调用了这个方法就是谁的实例
//JNIEXPORT jint JNICALL Java_tl_com_testmaterialdesign_navigation9_MyJniClass_sum
//        (JNIEnv * env, jobject obj, jint a, jint b)
//{
//    return a + b;
//}
//
///*
//* Class:     tl_com_testmaterialdesign_navigation9_MyJniClass
//* Method:    append
//* Signature: (Ljava/lang/String;)Ljava/lang/String;
//*/
//JNIEXPORT jstring JNICALL Java_tl_com_testmaterialdesign_navigation9_MyJniClass_append
//        (JNIEnv * env, jobject obj, jstring str)
//{
//    return str;
//}
//
///*
//* Class:     tl_com_testmaterialdesign_navigation9_MyJniClass
//* Method:    operateEle
//* Signature: ([I)[I
//*/
//JNIEXPORT jintArray JNICALL Java_tl_com_testmaterialdesign_navigation9_MyJniClass_operateEle
//        (JNIEnv * env, jobject obj, jintArray array)
//{
//
//    int length = (*env)->GetArrayLength(env, array);
//
//    // 得到是array数组的指针
//    jint *intArray = (*env)->GetIntArrayElements(env, array, JNI_FALSE);
//
//    int i = 0;
//
//    for(; i<length; i++)
//    {
//        intArray[i] += 10;
//    }
//    return array;
//}
//
///*
//* Class:     tl_com_testmaterialdesign_navigation9_MyJniClass
//* Method:    checkPwd
//* Signature: (Ljava/lang/String;)Z
//*/
//JNIEXPORT jboolean JNICALL Java_tl_com_testmaterialdesign_navigation9_MyJniClass_checkPwd
//        (JNIEnv * env, jobject obj, jstring str)
//{
//
//    char *pwd = _JString2CStr(env, str);
//
//    if(strcmp(pwd,"123456"))
//    {
//        return JNI_FALSE;
//    }
//    else
//    {
//        return JNI_TRUE;
//    }
//
//}
//
//
///****************************c语言用反射调用java函数****************************************/
///*
//* Class:     tl_com_testmaterialdesign_navigation9_MyJniClass
//* Method:    callbackAdd java触发c语言函数，c语言函数里调用java函数
//* Signature: ()V
//*/
//JNIEXPORT void JNICALL Java_tl_com_testmaterialdesign_navigation9_MyJniClass_callbackAdd
//(JNIEnv* env, jobject obj)
//{
//
//    // 进入应用的app/build/intermediates/classes/debug目录用javap -s 全类名得到函数签名符号形式
//
//    // 1，得到class
//    jclass clazz = (*env)->FindClass(env, "tl/com/testmaterialdesign/navigation9/MyJniClass");
//
//    // 2，得到方法
//    jmethodID method = (*env)->GetMethodID(env, clazz, "add", "(II)I");
//
//    // 3，实例化java对象
//    jobject myJniClazz = (*env)->AllocObject(env, clazz);
//
//    // 4，调用方法
//    jint result = (*env)->CallIntMethod(env, myJniClazz, method, 100, 200);
//    LOGD("result = %d\n", result);
//
//}
//
///*
//* Class:     tl_com_testmaterialdesign_navigation9_MyJniClass
//* Method:    callbackHelloFromJava java触发c语言函数，c语言函数里调用java函数
//* Signature: ()V
//*/
//JNIEXPORT void JNICALL Java_tl_com_testmaterialdesign_navigation9_MyJniClass_callbackHelloFromJava
//(JNIEnv* env, jobject obj)
//{
//
//    // 1，得到class
//    jclass clazz = (*env)->FindClass(env, "tl/com/testmaterialdesign/navigation9/MyJniClass");
//
//    // 2，得到方法
//    jmethodID method = (*env)->GetMethodID(env, clazz, "helloFromJava", "()V");
//
//    // 3，实例化java对象
//    jobject myJniClazz = (*env)->AllocObject(env, clazz);
//
//    // 4，调用方法
//    (*env)->CallVoidMethod(env, myJniClazz, method);
//    LOGD("java里的方法被成功调用\n");
//}
//
//
///*
//* Class:     tl_com_testmaterialdesign_navigation9_MyJniClass
//* Method:    callbackPrintString java触发c语言函数，c语言函数里调用java函数
//* Signature: ()V
//*/
//JNIEXPORT void JNICALL Java_tl_com_testmaterialdesign_navigation9_MyJniClass_callbackPrintString
//(JNIEnv* env, jobject obj)
//{
//    // 1，得到class
//    jclass clazz = (*env)->FindClass(env, "tl/com/testmaterialdesign/navigation9/MyJniClass");
//
//    // 2，得到方法
//    jmethodID method = (*env)->GetMethodID(env, clazz, "printString", "(Ljava/lang/String;)V");
//
//    // 3，实例化java对象
//    jobject myJniClazz = (*env)->AllocObject(env, clazz);
//
//    jstring str = (**env).NewStringUTF(env, "i am tianlin");
//    // 4，调用方法
//    (*env)->CallVoidMethod(env, myJniClazz, method, str);
//
//    LOGD("java里的方法被成功调用");
//}
//
///*
//* Class:     tl_com_testmaterialdesign_navigation9_MyJniClass
//* Method:    callbackSayHello java触发c语言函数，c语言函数里调用java函数
//* Signature: ()V
//*/
//JNIEXPORT void JNICALL Java_tl_com_testmaterialdesign_navigation9_MyJniClass_callbackSayHello(JNIEnv* env,
//jobject obj)
//{
//    // 1，得到class
//    jclass clazz = (*env)->FindClass(env, "tl/com/testmaterialdesign/navigation9/MyJniClass");
//
//    // 2，得到方法
//    jmethodID method = (*env)->GetStaticMethodID(env, clazz, "sayHello", "(Ljava/lang/String;)V");
//
//    jstring str = (**env).NewStringUTF(env, "i am tianlin from c");
//    // 3，调用方法
//    (*env)->CallStaticVoidMethod(env, clazz, method, str);
//
//    LOGD("java里的静态方法被成功调用");
//}