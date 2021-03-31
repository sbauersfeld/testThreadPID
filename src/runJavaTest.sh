JAVA_HOME=$(readlink -f /usr/bin/javac | sed "s:bin/javac::")
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:test
javac test/GetThreadInfo.java
javah test.GetThreadInfo
mv test_GetThreadInfo.h test

cd test
gcc -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux -fPIC -shared test_GetThreadInfo.c -o libtest_GetThreadInfo.so
cd ..
javac test/Main.java
java test.Main