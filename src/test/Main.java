package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    static int maxThreads = 4;
    static ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(maxThreads);

    public static String getPid() throws IOException,InterruptedException {

        ArrayList<String> commands = new ArrayList();
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add("echo $PPID");

        ProcessBuilder pb = new ProcessBuilder(commands);

        Process pr = pb.start();
        pr.waitFor();

        if (pr.exitValue() == 0) {
            BufferedReader outReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            return outReader.readLine().trim();
        } else {
            System.out.println("Error while getting PID");
            return "";
        }
    }

    static void threadCode(boolean spawn)  {
        int tid = GetThreadInfo.get_tid();
        int pid = GetThreadInfo.get_pid();
        int ppid = GetThreadInfo.get_ppid();
        int threadLevel = 1;
        if (!spawn) {
            threadLevel = 2;
        }

        String spid = "";
        try {
            spid = getPid();
        } catch (Exception err) {

        }

        System.out.println(String.format("Child thread LEVEL %s TID %s PID %s PPID %s. Spawned process sees its PPID as %s.", threadLevel, tid, pid, ppid, spid));
        if (spawn) {
            executor.submit(() -> threadCode(false));
        }
    }

    public static void main(String[] args) {
        try {
            int tid = GetThreadInfo.get_tid();
            int pid = GetThreadInfo.get_pid();
            int ppid = GetThreadInfo.get_ppid();
            String spid = getPid();

            System.out.println(String.format("Main thread TID %s PID %s PPID %s. Spawned process sees its PPID as %s.", tid, pid, ppid, spid));

            System.out.println(String.format("Running executor pool with a maximum of %s concurrent threads.", maxThreads));

            executor.submit(() -> threadCode(true));
            executor.submit(() -> threadCode(true));
            executor.submit(() -> threadCode(true));

            executor.awaitTermination(1000, TimeUnit.MILLISECONDS);

            System.out.println("Shutting down.");
            executor.shutdown();
        } catch (Exception err) {

        }
    }
}
