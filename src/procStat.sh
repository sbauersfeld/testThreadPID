if [[ $1 -eq "" ]]; then
  echo "Usage: procStat.sh <PID>"
  exit 1
fi

echo "Proc stats for PID/TID $1"
cat /proc/$1/status | grep -E "(Tgid|^Pid|PPid)"