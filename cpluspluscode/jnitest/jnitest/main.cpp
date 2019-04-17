#include "springboot_jni_JniApi.h"
#include "aes_encryptor.h"

/*
 * Class:     springboot_jni_JniApi
 * Method:    testHello
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_springboot_jni_JniApi_testHello
(JNIEnv *, jobject) {
	printf("this is C++ print ya hahahah");
}

/*
 * Class:     springboot_jni_JniApi
 * Method:    certificateValidation
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_springboot_jni_JniApi_certificateValidation
(JNIEnv *, jobject, jstring) {
	jboolean b = false;
	return b;
}

/*
 * Class:     springboot_jni_JniApi
 * Method:    encrypt
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_springboot_jni_JniApi_encrypt
(JNIEnv *env, jobject, jstring) {
	jstring s = env->NewStringUTF("This just a test for Android Studio NDK JNI developer!中文？");

	return s;
}

/*
 * Class:     springboot_jni_JniApi
 * Method:    decrypt
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_springboot_jni_JniApi_decrypt
(JNIEnv *env, jobject, jstring) {
	jstring s = env->NewStringUTF("This just a test for Android Studio NDK JNI developer!测试一下中文？？？？");

	

	return s;
}

/*
 * Class:     springboot_jni_JniApi
 * Method:    encrypt
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_springboot_jni_JniApi_encrypt__Ljava_lang_String_2Ljava_lang_String_2
(JNIEnv *, jobject, jstring, jstring) {
	
}

/*
 * Class:     springboot_jni_JniApi
 * Method:    decrypt
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_springboot_jni_JniApi_decrypt__Ljava_lang_String_2Ljava_lang_String_2
(JNIEnv *, jobject, jstring, jstring);