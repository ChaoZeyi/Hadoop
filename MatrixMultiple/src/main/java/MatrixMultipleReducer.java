import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author cherry
 * @date 2017/11/12 15:46
 */
public class MatrixMultipleReducer extends Reducer<Text, Text, Text, Text> {
    protected void reduce(Text key, Text value, Context context) throws IOException, InterruptedException {
        context.write(key, value);
    }
}
