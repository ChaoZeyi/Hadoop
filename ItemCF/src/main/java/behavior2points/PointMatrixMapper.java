package behavior2points;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author cherry
 * @date 2017/11/13 17:04
 */
public class PointMatrixMapper extends Mapper<LongWritable, Text, Text, Text>{
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] values = value.toString().split(",");
        String userId = values[0];
        String itemId = values[1];
        String score = values[2];
        context.write(new Text(itemId), new Text(userId+"_"+score));
    }
}
