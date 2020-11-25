import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

/**
 * 得到的数据对象为Context聚合之后的数据：map输出给Context，Context将结果进行聚合
 * 例如 mapper输出的值中 将同key的数据聚合（例 <hello,1> <hello,1>聚合为 <hello,{1,1}(一个Iterable对象)>
 * 然后再输出给reducer处理
 */
public class WcReducer  extends Reducer<Text,IntWritable,Text, IntWritable> {
    private IntWritable total = new IntWritable();
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
//      遍历包含n个1的Iterable对象
        for(IntWritable value:values){
//          此处可以写 sum+=1，但不推荐写1。原因待笔者研究
            sum+=value.get();
        }
//        包装结果并输出
        total.set(sum);
        context.write(key,total);
    }
}
