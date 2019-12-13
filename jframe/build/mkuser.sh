#!/bin/bash

printf "
###############################
#    快速创建用户/组/SFTP脚本   #
#      edited by jackson      #
#  请按说明操作，如有默认回车即可 #
###############################
"

while :; do echo
	read -p "请输入用户名: " USER
	if [ -n "$USER" ];then
		ex_user=`egrep "^$USER" /etc/passwd`
		if [ -n "$ex_user" ];then
			echo "用户$USER已存在"
		else
			break
		fi
	fi
done


while :; do echo
	read -p "请输入组名: " GROUP
	if [ -n "$GROUP" ];then
		ex_group=`egrep "^$GROUP" /etc/group`
		if [ -n "$ex_group" ];then
			echo "用户组$GROUP已存在，跳过创建"
		else
			groupadd $GROUP
		fi
		break
	fi
done

echo "您输入的用户名为：$USER，组名为：$GROUP"
echo
echo "请输入用户类型: ssh用户|sftp用户"
read -p "ssh or sftp(默认ssh): " USER_TYPE
[ -z "$USER_TYPE" ] && USER_TYPE="ssh"
if [[ ! $USER_TYPE == 'ssh' && ! $USER_TYPE == 'sftp' ]];then
	echo "请输入正确的类型"
fi

if [ "$USER_TYPE" == 'ssh' ];then
	useradd -g $GROUP $USER
	echo "用户$USER已创建"
fi

if [ "$USER_TYPE" == 'sftp' ];then
	useradd -g $GROUP -s /bin/false $USER
	echo "用户$USER已创建"
	echo
	echo "*请先确保已正确配置/etc/ssh/sshd_config文件，并将组及主目录配置完全"
	while :; do
		read -p "请输入FTP主目录[如/home/data/sftp]: " FTP_PATH
		if [ -e $FTP_PATH ];then
			mkdir -p $FTP_PATH/$USER
			usermod -d $FTP_PATH/$USER $USER 
			chown root:$GROUP $FTP_PATH/$USER
			chmod 755 $FTP_PATH/$USER
			mkdir $FTP_PATH/$USER/main
			chown $USER:$GROUP $FTP_PATH/$USER/main
			chmod 755 $FTP_PATH/$USER/main
			break
		else
			echo "FTP目录不存在"
		fi
	done

	read -p "是否打印sshd_config所需配置[y/n]: " SFTP_CFG_PRINT
	if [ "$SFTP_CFG_PRINT" == 'y' ];then
		printf "
Subsystem       sftp    internal-sftp
Match Group sftp
ChrootDirectory $FTP_PATH/%u
ForceCommand    internal-sftp
AllowTcpForwarding no
X11Forwarding no
		"
	fi
fi

while :; do
	read -p "是否设置密码[y/n]: " PWD_YN
	if [[ ! $PWD_YN =~ ^[y,n]$ ]];then
		echo "输入错误[y/n]"
	else
		if [ "$PWD_YN" == 'y' ];then
			passwd $USER
		fi
		break
	fi
done

while :; do
	read -p "是否设置SSH密钥登陆[y/n]: " SSH_YN
	if [[ ! $SSH_YN =~ ^[y,n]$ ]];then
		echo "输入错误[y/n]"
	else
		break
	fi
done

if [ "$SSH_YN" == 'y' ];then
	echo "请根据提示操作生成密钥[密钥需要在用户根目录/home/{user}/.ssh]"
	su - $USER -c "ssh-keygen -t rsa"
	while :; do
		read -p "请输入生成的公钥文件名(默认id_rsa.pub): " SSH_NAME
		[ -z "$SSH_NAME" ] && SSH_NAME="id_rsa.pub"
		if [ -n $SSH_NAME ];then
			if [ ! -e /home/$USER/.ssh/$SSH_NAME ];then
				echo "公钥文件不存在"
				continue
			fi
			su - $USER -c "cat /home/$USER/.ssh/$SSH_NAME > /home/$USER/.ssh/authorized_keys"
			chmod 700 /home/$USER/.ssh
			chmod 600 /home/$USER/.ssh/*
			systemctl restart sshd
			break
		else
			echo "请输入公钥文件名"
		fi
	done
fi

echo
echo "已创建用户，主目录：/home/$USER，组：$GROUP，类型：$USER_TYPE"
if [ "$SSH_YN" == 'y' ];then
	echo "私钥文件目录：/home/$USER/.ssh/"
fi
if [ "$USER_TYPE" == 'sftp' ];then
	echo "FTP主目录：$FTP_PATH"
fi

