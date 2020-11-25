package paichong;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.Tool;

import java.io.IOException;

public class MaxWord  extends Configured implements Tool {

    public static class MaxWordMapper extends Mapper<LongWritable, Text,Text,LongWritable>{
//        map端局部最大的单词
        Text keyOut = new Text();
//        map端局部最大值的封装对象
        LongWritable valueOut = new LongWritable();
//        map端的局部最大值
        Long maxNum = Long.MIN_VALUE;
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String [] arr = line.split("\t");

//            防御式编程
            if (arr.length !=2){
                context.getCounter("hainiu ","bad line").increment(1l);
                return;
            }

            String word  = arr[0];

            long num = Long.parseLong(arr[1]);

//            通过这样的方式获取最大的值
            if(maxNum< num){
                keyOut.set(word);
                maxNum = num;
            }

        }
        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException{
//  通过cleanup数据端局部最大值
            valueOut.set(maxNum);
            context.write(keyOut,valueOut);
            System.out.println(keyOut.toString()+maxNum);
        }


    }
    public static class MaxWordReducer extends Reducer<Text,LongWritable,Text,LongWritable> {
//        reducer 端的全局最大值
        Text keyOut = new Text();
//        reducer端全局最大值封装的对象

        LongWritable valueOut = new LongWritable();

//        reduce端的最大值
        long maxNum = Long.MIN_VALUE;

        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
//            求全局的最大值
            for (LongWritable w : values){
                long num = w.get();
                if(maxNum<num){
                    maxNum = num;
                    keyOut.set(key.toString());
                }
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            valueOut.set(maxNum);
            context.write(keyOut,valueOut);
            System.out.println(keyOut.toString());
        }
    }

    public int run(String[] args) throws Exception {
        return 0;
    }
}
