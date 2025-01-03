#!/bin/bash

# 设置时区
TZ=Asia/Shanghai
# JVM参数
JAVA_OPTS="-server -Xms1g -Xmx1g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m -XX:+HeapDumpOnOutOfMemoryError"

JAVA_OPTS="${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom"
JAVA_OPTS="${JAVA_OPTS} -Dfile.encoding=UTF-8"
JAVA_OPTS="${JAVA_OPTS} -Duser.timezone=${TZ}"
JAVA_OPTS="${JAVA_OPTS} -Dserver.port=8090"
JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=prod"

case $1 in
"start") {
  echo "-------------------- 启动 bryce-boot --------------------"
  # shellcheck disable=SC2086
  nohup java -jar $JAVA_OPTS bryce-boot-server-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
};;

"stop") {
  echo "-------------------- 停止 bryce-boot --------------------"
  # shellcheck disable=SC2009
  ps -ef | grep bryce-boot | grep -v grep | awk '{print $2}' | xargs kill -9
};;

"restart") {
  echo "-------------------- 重启 bryce-boot --------------------"
  # shellcheck disable=SC2009
  pid=$(ps -ef | grep bryce-boot | grep -v grep | awk '{print $2}')
  if [ -n "$pid" ]; then
    kill -9 "$pid"
  fi
  # shellcheck disable=SC2086
  nohup java -jar $JAVA_OPTS bryce-boot-server-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
};;

"status") {
  echo "-------------------- bryce-boot 状态 --------------------"
  # shellcheck disable=SC2009
  pid=$(ps -ef | grep bryce-boot | grep -v grep | awk '{print $2}')
  if [ -z "$pid" ]; then
    echo "-------------------- bryce-boot 未运行 --------------------"
  else
    echo "-------------------- bryce-boot 正在运行，pid: $pid --------------------"
  fi
};;

"log") {
  if [ "$2" == "info" ] || [ "$2" == "warn" ] || [ "$2" == "error" ]; then
     echo "-------------------- bryce-boot $2 日志 --------------------"
    tail -n 75 logs/"$2".log
  elif [ -n "$2" ]; then
    echo "Usage: brc.sh log [info|warn|error]"
  else
    echo "-------------------- bryce-boot info 日志 --------------------"
    tail -n 75 logs/info.log
  fi
};;

"help") {
  echo "Usage: brc.sh [start|stop|restart|status|log]"
  echo "       brc.sh log [info|warn|error]"
};;

esac