package test;

public class GetThreadInfo {
    public static native int get_tid();
    public static native int get_pid();
    public static native int get_ppid();

    static {
        System.out.println("Loading JNI lib.");
        System.loadLibrary("test_GetThreadInfo");
        System.out.println("Done loading JNI lib.");
    }
}