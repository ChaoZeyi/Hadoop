import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author cherry
 * @date 2017/11/12 15:46
 */
public class MatrixMultipleReducer extends Reducer<Text, Text, Text, Text> {
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        for(Text value: values)
        {
            sb.append(value+",");
        }
        String result = null;
        if(sb.toString().endsWith(","))
            result = sb.substring(0, sb.length()-1);
        context.write(key, new Text(result));
    }
}
