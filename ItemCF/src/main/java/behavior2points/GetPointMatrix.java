package behavior2points;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author cherry
 * @date 2017/11/13 17:03
 */
public class GetPointMatrix {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if(args.length != 2)
            System.err.println("please give input and output path");
        Job job = new Job();
        job.setJobName("behavior to point matrix");
        job.setJarByClass(GetPointMatrix.class);

        job.setMapperClass(PointMatrixMapper.class);
        job.setReducerClass(PointMatrixReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileSystem fs = FileSystem.get(new Configuration());
        Path path = new Path(args[1]);
        if(fs.exists(path))
            fs.delete(path, true);
        FileOutputFormat.setOutputPath(job, path);
        //参数为true，表示实时打印作业的运行情况
        System.exit(job.waitForCompletion(true)?0:1);
    }
}
