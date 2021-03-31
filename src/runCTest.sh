printf "Run 'runCTest.sh &' to inspect /proc/<pid>/status\n\n"
cd ctest
gcc ctest.c -o ctest.out -lpthread
./ctest.out