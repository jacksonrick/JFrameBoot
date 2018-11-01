#!/bin/bash
# Author: jackson
# Version: 1.0

proj_name="test" 	# 项目名称，英文
path="/home/data/backup"	# 备份主目录，不存在会创建
logfile="$path/backup.log"	# 日志目录
path_sql="$path/sql" 	# 数据库备份目录
path_www="$path/www" 	# 代码备份目录
date=$(date '+%Y-%m-%d-%H-%M')	# 备份目录格式

code_path="/home/zy/zentao/app"	# 代码绝对路径；如果是Docker容器，填写"容器名称:绝对路径"
code_in_docker=0 	# 代码是否在Docker容器中；可选（1，0）

db_type="postgres" 	# 数据库类型，可选（mysql，postgres）
db_is_docker=1		# 数据库是否是Docker容器；可选（1，0）
db_name="test" 		# 数据库名称
db_host="postgres" 	# 数据库地址；空表示在本地；如果是Docker容器，填写容器名称
db_port="" 		# 数据库端口；空表示在本地；如果是Docker容器，可不填
db_username="postgres" 	# 数据库用户名
db_password="12345678" 	# 数据库密码

# 预创建目录
if [ ! -d $path ];then
  echo "`date "+%Y-%m-%d %H:%M:%S"` 创建目录:$path" >> $logfile 2>&1
  mkdir -p $path
fi

cd $path
if [ ! -d $path_sql ];then
  echo "`date "+%Y-%m-%d %H:%M:%S"` 创建目录:sql,www" >> $logfile 2>&1
  mkdir {$path_sql,$path_www}
fi

# 备份代码
code_filename=$proj_name.$date.tar.gz
if [ $code_in_docker -eq 0 ]; then
  tar -czf $code_filename -C $code_path .
  mv $code_filename $path_www
  echo "`date "+%Y-%m-%d %H:%M:%S"` 代码已经备份到:$path_www/$code_filename" >> $logfile 2>&1
elif [ $code_in_docker -eq 1 ]; then
  docker cp $code_path $path_www/tmp
  cd $path_www
  tar --remove-files -czf $code_filename -C tmp .
  echo "`date "+%Y-%m-%d %H:%M:%S"` 代码已从容器备份到:$path_www/$code_filename" >> $logfile 2>&1
fi
cd $path

# 备份数据库
db_filename=$proj_name.$date.sql
if [ $db_is_docker -eq 0 ]; then
  host=""
  if [ $db_type = "mysql" ]; then
    if [ ! -z $db_host]; then
      host="-h$db_host -P$db_port "
    fi
    mysqldump $host-u$db_username -p$db_password $db_name > $path_sql/$db_filename
  elif [ $db_type = "postgres" ]; then
    if [ ! -z $db_host]; then
      host="-h $db_host -p $db_port "
    fi
    pg_dump $host-U $db_username $db_name > $path_sql/$db_filename
  fi
  echo "`date "+%Y-%m-%d %H:%M:%S"` 数据库已备份到:$path_sql/$db_filename" >> $logfile 2>&1
elif [ $db_is_docker -eq 1 ]; then
  if [ $db_type = "mysql" ]; then
    docker exec $db_host bash -c "mysqldump -u$db_username -p$db_password $db_name > /home/$proj_name.sql"
    docker cp $db_host:/home/$proj_name.sql $path_sql/$db_filename
  elif [ $db_type = "postgres" ]; then
    docker exec $db_host bash -c "pg_dump -U $db_username $db_name > /home/$proj_name.sql"
    docker cp $db_host:/home/$proj_name.sql $path_sql/$db_filename
  fi
  echo "`date "+%Y-%m-%d %H:%M:%S"` 数据库已从容器备份到:$path_sql/$db_filename" >> $logfile 2>&1
fi

echo "`date "+%Y-%m-%d %H:%M:%S"` 备份完毕;代码总数量：`cd $path_www && ls | wc -w`, 数据库总数量：`cd $path_sql && ls | wc -w`" >> $logfile 2>&1
