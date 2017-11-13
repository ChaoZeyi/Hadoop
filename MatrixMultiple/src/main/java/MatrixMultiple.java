/**
 * @author cherry
 * @date 2017/11/12 15:42
 */

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.*;

/**
 *
 */
public class MatrixMultiple {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if(args.length != 2)
        {
            System.err.println("please give two file path");
            System.exit(-1);
        }
        Job job = new Job();
        job.setJobName("matrix multiple");
        job.setJarByClass(MatrixMultiple.class);
//        String cache = "B.txt";
//        job.addCacheFile(new File(cache).toURI());
        job.setMapperClass(MatrixMultipleMapper.class);
        job.setReducerClass(MatrixMultipleReducer.class);
        //job.setCombinerClass(MatrixMultipleReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true)?0: 1);


    }
}
