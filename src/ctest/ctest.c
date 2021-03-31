#include <unistd.h>
#include <stdio.h>
#include <pthread.h>

#if __GLIBC__ == 2 && __GLIBC_MINOR__ < 30
#include <sys/syscall.h>
#define gettid() syscall(SYS_gettid)
#endif

void* thread_code(void *input) {
    int* spawn_thread = (int*) input;

    printf("Child thread LEVEL %d TID %ld PID %d PPID %d\n", *spawn_thread, gettid(), getpid(), getppid());

    if (*spawn_thread == 1) {
        pthread_t tid;
        int spawn_thread = 2;
        pthread_create(&tid, NULL, thread_code, &spawn_thread);
        pthread_join(tid, NULL);
    } else {
        printf("Looping forever so you can inspect my process stats...");
        while (1);
    }

    pthread_exit(NULL);
}

void main() {
    printf("Main thread TID %ld PID %d PPID %d\n", gettid(), getpid(), getppid());
    pthread_t tid;
    int spawn_thread = 1;
    pthread_create(&tid, NULL, thread_code, &spawn_thread);
    pthread_join(tid, NULL);
}