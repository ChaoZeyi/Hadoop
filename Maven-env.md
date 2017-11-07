## Maven使用

根据Apache官网的介绍：

> Apache Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.  

Maven的作用在于在项目的管理

### 下载安装

在官网下载最新版：

http://maven.apache.org/download.cgi

![maven-download.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/maven-download.png?raw=true)

下载下来是一个压缩包，直接解压，然后配置环境变量

只需要配置PATH变量，在后面加上../maven/bin（根据自己的文件解压路径）

然后验证 mvn -v：

![maven-success.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/maven-success.png?raw=true)

如果你看到上图所示的消息，就说明Maven已经安装成功了。

### 本地仓库配置

使用Maven比较方便的一点就是统一管理jar 包，这些 jar 包都存放在我们的本地仓库中，默认地址位于 C:\Users\用户名.m2 目录下，但因为随着我们的Maven项目越来越多，这个本地仓库也会越来越大，放在C盘总是不太好，所以我们修改一下这个默认地址。

首先新建一个本地仓库文件：

![maven-respo.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/maven-respo.png?raw=true)

然后在Maven目录下的/conf/settings.xml文件，添加一行：

![maven-settings.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/maven-settings.png?raw=true)

开发项目时项目首先会从本地仓库中获取 jar 包，当无法获取指定 jar 包的时候，本地仓库会从远程仓库（由Maven官方提供： http://search.maven.org/）下载 jar 包，并保存到本地仓库中以备将来使用。

### IntelliJ  IDEA配置Maven

这个链接里面讲的很详细：http://www.cnblogs.com/sigm/p/6035155.html

主要就是settings里面的一些配置，很简单！