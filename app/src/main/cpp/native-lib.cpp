#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_allhdvideofree_downloader_videodownloader_Utils_Constant_getMain(JNIEnv *env, jclass instance) {
    std::string hello = "https://apifromdev.com/nv/AllVideoCBX0128NV/V1/";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_allhdvideofree_downloader_videodownloader_Utils_Constant_getKey1(JNIEnv *env, jclass instance) {
    std::string hello = "Mbw3OZfvNersgFK2";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_allhdvideofree_downloader_videodownloader_Utils_Constant_getKey2(JNIEnv *env, jclass instance) {
    std::string hello = "FG0r4odf8wLPgRUo";
    return env->NewStringUTF(hello.c_str());
}
