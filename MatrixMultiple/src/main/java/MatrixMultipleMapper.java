import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.avro.generic.GenericData.StringType.String;

/**
 * @author cherry
 * @date 2017/11/12 15:45
 */
public class MatrixMultipleMapper extends Mapper<LongWritable, Text, Text, Text>{
    private List<String[]> matrixB = new ArrayList<String[]>();

    /*
    setup()，此方法被MapReduce框架仅且执行一次，在执行Map任务前，进行相关变量或者资源的集中初始化工作。
    若是将资源初始化工作放在方法map()中，导致Mapper任务在解析每一行输入时都会进行资源初始化工作，导致重复，程序运行效率不高！
cleanup(),此方法被MapReduce框架仅且执行一次，在执行完毕Map任务后，进行相关变量或资源的释放工作。
若是将释放资源工作放入方法map()中，也会导致Mapper任务在解析、处理每一行文本后释放资源，而且在下一行文本解析前还要重复初始化，导致反复重复，程序运行效率不高！
     */
    protected void setup(Context context) throws IOException, InterruptedException {
        FileReader fr = new FileReader("input/B.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while((line = br.readLine()) != null)
        {
            String[] row =line.split(":");
            String rowNumber = row[0];
            String[] columnValues = row[1].split(",");
            String[] matrixB_eachRow = new String[columnValues.length];
            for(int i = 0; i < columnValues.length; i++)
            {
                String value = columnValues[i].split("_")[1];
                matrixB_eachRow[i] = value;
            }
            matrixB.add(matrixB_eachRow);
        }
    }

    /**
     *
     * @param key  偏移量
     * @param value 值
     * @param context
     *
     */
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] row = value.toString().split(":");
        String rowNumber = row[0];
        String[] columnValues = row[1].split(",");
        for(int j = 0; j < matrixB.get(0).length; j++) {
            int res = 0;
            for (int i = 0; i < columnValues.length; i++) {
                String columnNumber = columnValues[i].split("_")[0];
                String value_ = columnValues[i].split("_")[1];
                res += Integer.parseInt(value_) * Integer.parseInt(matrixB.get(i)[j]);
            }

             String result = (j + "_" + res);
            context.write(new Text(rowNumber), new Text(result));
        }


    }
}
