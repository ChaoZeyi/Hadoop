# Hadoop伪分布式开发环境搭建

目前有一个台式机：win7系统+ubuntu虚拟机；一台Mac笔记本：win10系统+OS系统

考虑到机器的内存不是不够，安装多个虚拟机会有点卡，所以选择的是在一台机器上模拟Hadoop分布式。现在开始配置：

### 修改主机名（可选）

为了使你的虚拟机环境更具有标识性，可将主机名改为hadoop，说明这是一个hadoop的分布式开发环境

注意不同系统的修改主机名方式不一样，像CentOS就是在“/etc/sysconfig/network”文件里面，而Ubuntu就是直接修改“/etc/hostname”文件，或者直接使用图形化界面

![graph.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/graph.png?raw=true)

![graph-hostname.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/graph-hostname.png?raw=true)

修改完之后，命令行就是变成

![hostname.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/hostname.png?raw=true)

也可以使用  hostname 命令来查看

### 配置SSH

1. 检查是否已经安装了ssh：rpm -qa | grep ssh
2. 如果显示没有安装，则执行yum install ssh 或者sudo apt-get install yum
3. 开启ssh服务：service sshd start

### java环境配置

1. 在官网下载安装包：

   http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

2. 在“/home/cherry"下新建一个目录java，将下载好的.tar.gz文件放在该路径下，然后在该路径下解压

3. 配置环境变量，编辑gedit ~/.bashrc文件，在最后加上如下几行，需要注意的是，不能有多余的空格！

![java-env.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/java-env.png?raw=true)

4. 然后执行 source ~/.bashrc，使环境变量配置生效
5. 测试java环境是否成功

![java-success.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/java-success.png?raw=true)

**需要注意的是，一定要保证虚拟机系统的位数和java位数一致，不然会报cannot execute binary file的错误**

### Hadoop配置

1. 在官网下载安装包：

   http://www.apache.org/dyn/closer.cgi/hadoop/common/hadoop-2.7.4/hadoop-2.7.4.tar.gz

2. 在“/home/cherry"下新建一个目录hadoop，将下载好的.tar.gz文件放在该路径下，然后在该路径下解压

3. 配置环境变量，编辑gedit ~/.bashrc文件，在最后加上如下几行，同样不能有多余的空格！

   ![hadoop-env.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/hadoop-env.png?raw=true)

   **需要补充的是：最好把sbin目录也加进去，因为到最后启动集群的时候发现，start-dfs.sh命令是在sbin目录下的，所以将PATH目录改为：export PATH=\$PATH:\$HADOOP_HOME/bin:$HADOOP_HOME/sbin**

4. 然后执行 source ~/.bashrc，使环境变量配置生效

5. 测试hadoop环境是否成功![hadoop-success.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/hadoop-success.png?raw=true)

### 修改xml文件，配置分布式环境

1. /home/cherry/hadoop/hadoop-2.7.4/etc/hadoop/hadoop-env.sh

   ![java-home.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/java-home.png?raw=true)

2. /home/cherry/hadoop/hadoop-2.7.4/etc/hadoop/core-site.xml

   ![core-site.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/core-site.png?raw=true)

   **注意：如果不修改默认的临时文件地址，还是放在/tmp目录下的话，每次开机所有的临时文件都会被清除，hadoop namenode的一些配置也会被清除，导致每次开启服务时，都需要在重新执行hadoop namenode -format命令**

3. /home/cherry/hadoop/hadoop-2.7.4/etc/hadoop/hdfs-site.xml

   ![hdfs-site.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/hdfs-site.png?raw=true)

4. /home/cherry/hadoop/hadoop-2.7.4/etc/hadoop/mapred-site.xml

   ![mapred-site.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/mapred-site.png?raw=true)

5. /home/cherry/hadoop/hadoop-2.7.4/etc/hadoop/yarn-site.xml

   ![yarn-site.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/yarn-site.png?raw=true)

### 启动Hadoop环境

1. 格式化目录节点 hadoop namenode -format

   ![format.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/format.png?raw=true)

2. 启动hdfs集群

   ![start-dfs.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/start-dfs.png?raw=true)

3. 启动yarn集群

   ![start-yarn.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/start-yarn.png?raw=true)

4. 查看进程

   ![process.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/process.png?raw=true)

5. 网页查看集群

   ![success.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/success.png?raw=true)



### 参考资料：

- http://www.cnblogs.com/laov/p/3421479.html
- http://www.cnblogs.com/yedezhanghao/archive/2012/07/29/2614182.html

