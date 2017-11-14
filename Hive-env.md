### Hive安装

1. 在http://hive.apache.org页面下载apache-hive-x.y.2-bin.tar.gz(最好选择稳定版)
2. 解压   tar -zxvf  apache-hive-x.y.2-bin.tar.gz
3. 配置环境变量，gedit ~/.bashrc
4. export HIVE_HOME=/home/cherry/hive/apache-hive-2.3.1-bin
   export PATH=\$PATH:$HIVE_HOME/bin

测试是否安装成功：

1. start-dfs.sh
2. hive

正常进入Hive的shell环境，但执行show tables;报错：

> FAILED: SemanticException org.apache.hadoop.hive.ql.metadata.HiveException: java.lang.RuntimeException: Unable to instantiate org.apache.hadoop.hive.ql.metadata.SessionHiveMetaStoreClient

网上找了一下，解决办法如下：

1. 进入apache-hive-2.3.1-bin/conf目录，执行：

   cp hive-default.xml.template hive-default.xml

   cp hive-env.sh.template hive-env.sh

   cp hive-default.xml hive-site.xml

2. 安装msql，执行：

   sudo apt-get install mysql-server

3. 添加mysql驱动，下载mysql-connector-java-5.1.16-bin.jar文件并放到apache-hive-2.3.1-bin/lib目录下面

4. 修改hive-site.xml及hive-env.sh相关配置

   将hive-site.xml文件中的内容修改为如下所示：

   > <?xml version="1.0" encoding="UTF-8" standalone="no"?>
   > <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
   > <configuration>  
   >
   > ```
   > <property>  
   >     <name>javax.jdo.option.ConnectionURL</name>  
   >     <value>jdbc:mysql://localhost:3306/hive?createDatabaseIfNotExist=true</value>  
   >     <description>JDBC connect string for a JDBC metastore</description>      
   > </property>     
   > <property>   
   >     <name>javax.jdo.option.ConnectionDriverName</name>   
   >     <value>com.mysql.jdbc.Driver</value>   
   >     <description>Driver class name for a JDBC metastore</description>       
   > </property>                 
   >   
   > <property>   
   >     <name>javax.jdo.option.ConnectionUserName</name>  
   >     <value>hive</value>  
   >     <description>username to use against metastore database</description>  
   > </property>  
   > <property>    
   >     <name>javax.jdo.option.ConnectionPassword</name>  
   >     <value>123456</value>  
   >     <description>password to use against metastore database</description>    
   > </property>
   > ```
   >
   > </configuration>

   将hive-env.sh文件中的内容修改为如下所示：

   > export HADOOP_HEAPSIZE=1024
   >
   > HADOOP_HOME=/home/cherry/....  #这里设置成自己的hadoop路径
   >
   > export HIVE_CONF_DIR=/home/cherry/hive/apache-hive-2.3.1-bin/conf 
   >
   > export HIVE_AUX_JARS_PATH=/home/cherry/hive/apache-hive-2.3.1-bin/lib  

5. 在mysql里面创建hive用户，并赋予足够权限

   mysql -u root -p

   create user 'hive' identified by '123456';

   grant all privileges on *.* to 'hive' with grant option;

   flush privileges;

6. 设置元数据库

   schematool -initSchema -dbType mysql

7. 测试，运行：

   show tables;

   测试通过，安装成功！

