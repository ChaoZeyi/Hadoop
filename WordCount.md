## 第一个Hadoop程序：WordCount

### 在本地IDEA编辑

1. 新建maven项目
2. 配置pom.xml文件

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cherry</groupId>
    <artifactId>WordCount</artifactId>
    <version>1.0-SNAPSHOT</version>

    <repositories>
        <repository>
            <id>apache</id>
            <url>http://maven.apache.org</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-core</artifactId>
            <version>1.2.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-common</artifactId>
            <version>2.7.4</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-hdfs</artifactId>
            <version>2.7.4</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-client</artifactId>
            <version>2.7.4</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <artifactId>maven-dependency-plugin</artifactId>
                <configuration>
                    <excludeTransitive>false</excludeTransitive>
                    <stripVersion>true</stripVersion>
                    <outputDirectory>./lib</outputDirectory>
                </configuration>

            </plugin>
        </plugins>
    </build>

</project>
```

3. 在/src/main/java目录下编写主程序 WordCount.java

```java
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;
import java.util.StringTokenizer;
public class WordCount {
    //Mapper将输入键值对映射成一组中间格式的键值对集合，Mapper原型：Mapper<KEYIN,VALUEIN,KEYOUT,VALUEOUT>
    /*
    * Mapper包含
    * */
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException{

            StringTokenizer itr = new StringTokenizer(value.toString());
            while(itr.hasMoreTokens()){
                word.set(itr.nextToken());
                context.write(word, one);
            }
        }
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException{
            int sum = 0;
            for(IntWritable val : values){
                sum += val.get();
            }

            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException{
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true)?0:1);

    }
}
```

4. 生成jar包：

在项目上鼠标右键--》 Open Module Settings

![module-settings.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/module-settings.png?raw=true)

Artifacts --》 + --》 JAR --> From modules with dependencies

![add.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/add.png?raw=true)

然后选择自己的class

![select.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/select.png?raw=true)

必须强调一点的是：需要自己设置META-INF/MANIFEST.MF文件的位置，不能用默认的，推荐放在和src并排的目录下。(经过测试，在我的hadoop环境上确实是这样的)

![META-INF.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/META-INF.png?raw=true)

生成jar包时有两个选择，第一个extract to the target JAR指的是把你的主java程序打成jar包，下面那个的意思是除了把你的主java程序打成jar包，还会顺带所有的程序依赖jar包，这样生成的就是一个文件夹下面多个jar包！

我这里选择的是第一种方式！

然后就是根据默认选项一直NEXT，到最后OK

最后一步：Build --》Build Artifacts --》WordCount.jar --> Build

![jar.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/jar.png?raw=true)

然后就会出来这么一个小窗口

![build-jar.png](https://github.com/ChaoZeyi/Hadoop/blob/master/pics/build-jar.png?raw=true)

到这里，jar包就打好了。

### Hadoop环境下运行jar包

1. 将上一步生成的jar拷到/home/cherry/hadoop-2.7.4的目录下

2. 在/home/cherry/hadoop-2.7.4的目录下新建一个文件夹file，里面存储input01.txt和input02.txt两个文件，作为程序的输入文件

3. 在远程HDFS上创建输入文件夹

   在/home/cherry/hadoop-2.7.4下运行     hadoop fs -mkdir /hdfsInput

4. 将本地的文件上传到远程输入文件夹

   在/home/cherry/hadoop-2.7.4下运行     hadoop fs -put file/input*.txt  /hdfsInput

   *是通配符，为了匹配input01.txt和input02.txt两个输入文件

5. 运行jar包

   在/home/cherry/hadoop-2.7.4下运行     hadoop jar WordCount.jar /hdfsInput /hdfsOutput

   需要注意的是：在运行该命令时，/hdfsOutput一定是不存在的，如果存在该文件，则会报错

### 查看运行结果

在localhost:50070上可以直接看到hdfsInput和hdfsOutput两个文件夹及其中的文件，运行结果保存在hdfsOutput文件夹下的part-r-00000文件里面。

如果要在命令行下面查看，在/home/cherry/hadoop-2.7.4下运行     hadoop fs -ls  /hdfsOutput查看hdfsOutput文件夹的所有文件，然后 hadoop fs -cat  /hdfsOutput/*，查看hdfsOutput文件夹下的所有文件内容

### 参考资料：

http://www.cnblogs.com/davidgu/p/6140927.html

http://www.cnblogs.com/blog5277/p/5920560.html

http://www.cnblogs.com/madyina/p/3708153.html