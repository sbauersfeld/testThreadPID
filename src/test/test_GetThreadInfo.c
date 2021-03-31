#include <jni.h>
#include <unistd.h>
#include "test_GetThreadInfo.h"

#if __GLIBC__ == 2 && __GLIBC_MINOR__ < 30
#include <sys/syscall.h>
#define gettid() syscall(SYS_gettid)
#endif

JNIEXPORT jint JNICALL
Java_test_GetThreadInfo_get_1tid(JNIEnv *env, jobject obj) {
    jint tid = gettid();
    return tid;
}

JNIEXPORT jint JNICALL
Java_test_GetThreadInfo_get_1pid(JNIEnv *env, jobject obj) {
    jint pid = getpid();
    return pid;
}

JNIEXPORT jint JNICALL
Java_test_GetThreadInfo_get_1ppid(JNIEnv *env, jobject obj) {
    jint ppid = getppid();
    return ppid;
}