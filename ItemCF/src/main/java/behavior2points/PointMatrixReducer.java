package behavior2points;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cherry
 * @date 2017/11/13 17:21
 */
public class PointMatrixReducer extends Reducer<Text, Text, Text, Text> {
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //统计加和
        Map<String, Integer> userScores = new HashMap<String, Integer>();
        for(Text value : values)
        {
            String[] infos = value.toString().split("_");
            String userId = infos[0];
            String score = infos[1];
            if(userScores.containsKey(userId))
            {
                int preScores = userScores.get(userId);
                userScores.put(userId, preScores + Integer.valueOf(score));
            }else{
                userScores.put(userId, Integer.valueOf(score));
            }

        }
        StringBuilder sb = new StringBuilder();
        for(String keyUser : userScores.keySet())
        {
            int score = userScores.get(keyUser);
            sb.append(keyUser + "_" + score + ",");
        }
        String result = null;
        if(sb.toString().endsWith(","))
            result = sb.substring(0, sb.length());
        context.write(key, new Text(result));
    }
}
