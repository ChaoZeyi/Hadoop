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
    setup()���˷�����MapReduce��ܽ���ִ��һ�Σ���ִ��Map����ǰ��������ر���������Դ�ļ��г�ʼ��������
    ���ǽ���Դ��ʼ���������ڷ���map()�У�����Mapper�����ڽ���ÿһ������ʱ���������Դ��ʼ�������������ظ�����������Ч�ʲ��ߣ�
cleanup(),�˷�����MapReduce��ܽ���ִ��һ�Σ���ִ�����Map����󣬽�����ر�������Դ���ͷŹ�����
���ǽ��ͷ���Դ�������뷽��map()�У�Ҳ�ᵼ��Mapper�����ڽ���������ÿһ���ı����ͷ���Դ����������һ���ı�����ǰ��Ҫ�ظ���ʼ�������·����ظ�����������Ч�ʲ��ߣ�
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
     * @param key  ƫ����
     * @param value ֵ
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
