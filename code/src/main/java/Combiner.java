import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import java.io.IOException;

public class Combiner extends Mapper<LongWritable, Text,Text,LongWritable> {

    Text keyOut = new Text();

    LongWritable valueOut = new LongWritable(1);

    @Override
    protected void map(LongWritable key,Text value,Context context)throws IOException,InterruptedException{

//        context连接mapred的上下文
//        one world
        String line = value.toString();

        String [] arr = line.split(" ");

        for(String word:arr){

            keyOut.set(word);
            context.write(keyOut,valueOut);
        }
    }


    public  static class WcCombiner extends Reducer<Text,LongWritable,Text,LongWritable> {
        LongWritable valueOut = new LongWritable();
        long sum = 0l;
        @Override
        protected void reduce(Text key,Iterable<LongWritable> values,Context context) throws IOException, InterruptedException {
            for(LongWritable w:values){
                long num = w.get();
                sum +=num;
            }
            valueOut.set(sum);
            context.write(key,valueOut);
        }
    }

    public static class WcReducer extends Reducer<Text,LongWritable,Text,LongWritable>{
        LongWritable  valueOut = new LongWritable();

        @Override
        protected void reduce(Text key,Iterable<LongWritable> values,Context context) throws IOException, InterruptedException {
            long sum = 0l;
            for(LongWritable w:values){
                sum+=w.get();
            }

            valueOut.set(sum);
            context.write(key,valueOut);

        }

    }
    public static void main(String[] args) throws IOException {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf,"wc");
        job.setMapperClass(WcMapper.class);

        job.setReducerClass(WcReducer.class);


        job.setMapOutputKeyClass(Text.class);

        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);









    }
}
