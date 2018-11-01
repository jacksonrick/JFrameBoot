#!/bin/bash
# Author: jackson
# Version: 1.0

path="/home/data/backup"	# 主目录，与备份目录相同
def_sh="/home/data/backup.sh"	# 默认备份脚本，可单独指定
logfile="$path/backup.log"	# 日志文件
# echo日志在/var/spool/mail/{USER}
# 定时器在/var/spool/cron/{USER}

printf "
##################################################
#       服务端自动备份程序，请选择类型           #
#       1 - 立刻执行                             #       
#       2 - 指定时间执行                         #
#       3 - 指定备份脚本                         #
#       4 - 停止定时备份任务                     #
##################################################
"
function execron() {
  local newsh=$1
  echo -n "请输入指定时间[格式 时/分，如23:30]: "
  read time
  echo "`date "+%Y-%m-%d %H:%M:%S"` 指定时间执行备份脚本，时间:${time}，脚本:${newsh}" >> $logfile 2>&1
  minute=`echo ${time} | cut -d \: -f 2`
  hour=`echo ${time} | cut -d \: -f 1`
  echo "${minute} ${hour} * * * ${newsh}" > ${path}/cron
  crontab ${path}/cron
  echo "任务已提交，用户`whoami`，将在每日${time}执行${newsh}"
}

# 预创建目录
if [ ! -d $path ];then
  mkdir -p $path
  echo "`date "+%Y-%m-%d %H:%M:%S"` 创建目录:$path" >> $logfile 2>&1
fi

read type
case $type in
1)
  echo "正在备份，请稍后..."
  $def_sh
  echo "备份已完成"
  echo "`date "+%Y-%m-%d %H:%M:%S"` 即时备份" >> $logfile 2>&1
;;
2)
  execron $def_sh
;;
3)
  while :; do echo
    read -p "请输入备份脚本的绝对路径:" newsh
    if [ -e $newsh ]; then
      execron $newsh
      break
    else
      echo "备份脚本不存在，请重新输入"   
    fi
  done
;;
4)
  crontab -r
  echo "`date "+%Y-%m-%d %H:%M:%S"` 备份任务已停止" >> $logfile 2>&1
  echo "备份任务已停止"
;;
*)
  echo "类型错误"
;;
esac

