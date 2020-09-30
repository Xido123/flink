
#!/bin/bash
BASE_PATH="$( cd "$( dirname "$0"  )" && pwd  )"
get_char() {
 SAVEDSTTY=`stty -g`
 stty -echo
 stty cbreak
 dd if=/dev/tty bs=1 count=1 2> /dev/null
 stty -raw
 stty echo
 stty $SAVEDSTTY
}
 
echo 在执行本脚本前您需注意：
echo 1、已经安装Ubuntu 14.04的64位版本
echo 2、当前帐户为hadoop，并已为其添加管理员权限
echo 3、已将Hadoop压缩包放置到‘/home/hadoop/下载/’目录下
echo 声明：本脚本为自用脚本，仅供参考！
echo ""
echo ""
echo "请按任意键继续!"
char=`get_char`
echo ""
 
sudo apt-get update
# 更新APT
sudo apt-get install -y vim openssh-server openjdk-7-jre openjdk-7-jdk
# 安装vim、SSH、OpenJDK 7
cd ~
dpkg -L openjdk-7-jdk | grep '/bin/javac'
sed -i '1i\export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64' .bashrc
# 在‘.bashrc’文件中第一行(1)的前面(i)添加export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
source ~/.bashrc
echo $JAVA_HOME
java -version
$JAVA_HOME/bin/java -version
# 配置Java运行环境
sudo tar -zxvf ~/下载/hadoop-2.7.1.tar.gz -C /usr/local
cd /usr/local/
sudo mv ./hadoop-2.7.1/ ./hadoop
sudo chown -R hadoop ./hadoop
cd /usr/local/hadoop
./bin/hadoop version
# 安装Hadoop
cd /usr/local/hadoop/etc/hadoop
sed -i '/<configuration>/a\<property>\n<name>hadoop.tmp.dir</name>\n<value>file:/usr/local/hadoop/tmp</value>\n<description>Abase for other temporary directories.</description>\n</property>\n<property>\n<name>fs.defaultFS</name>\n<value>hdfs://localhost:9000</value>\n</property>' core-site.xml
sed -i '/<configuration>/a\<property>\n<name>dfs.replication</name>\n<value>1</value>\n</property>\n<property>\n<name>dfs.namenode.name.dir</name>\n<value>file:/usr/local/hadoop/tmp/dfs/name</value>\n</property>\n<property>\n<name>dis.datanode.data.dir</name>\n<value>file:/usr/local/hadoop/tmp/dfs/data</value>\n</property>' hdfs-site.xml
# 修改配置文件
cd /usr/local/hadoop
./bin/hdfs namenode -format
# 格式化名称节点
cd /usr/local/hadoop
./sbin/start-dfs.sh
# 启动Hadoop
jps
# 查看进程
echo 享受～
