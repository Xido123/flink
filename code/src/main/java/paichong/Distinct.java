package paichong;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class Distinct extends Configured implements Tool {


    public static class DistinctMapper extends Mapper<LongWritable,Text,Text, NullWritable> {
        /**
         * map输出的key
         */
        Text keyOut = new Text();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            System.out.println(key.get()+" "+ value);
            String [] splits = line.split(" ");
            for(String word:splits){
                keyOut.set(word);
                context.write(keyOut,NullWritable.get());
            }
        }
    }

    public static class DistinctReducer extends Reducer<Text,NullWritable,Text,NullWritable>{
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key,NullWritable.get());
        }
    }

    public int run(String[] args)throws Exception{
        Configuration cnf = this.getConf();
//       加载maopreduce的配置，生成mapreduce的job对象
        Job job = Job.getInstance(cnf,"dictinct");

//        设置打包时用的class
        job.setJarByClass(Distinct.class);
//        设置job运行时使用的mapperClass
        job.setMapperClass(DistinctMapper.class);
//        设置job运行时使用的reduceclass
        job.setReducerClass(DistinctReducer.class);
//       设置reduce的个数
        job.setNumReduceTasks(1);
//        设置map输出的keyclas
        job.setMapOutputKeyClass(Text.class);
//        设置map输出的valueclass
        job.setMapOutputValueClass(NullWritable.class);
//        设置最终输出的keyclass
        job.setOutputKeyClass(Text.class);
//        设置最终输出的valueclass
        job.setOutputValueClass(NullWritable.class);
//        设置inputformatclass
        job.setInputFormatClass(TextInputFormat.class);
//        设置outputformatClass
        job.setOutputFormatClass(TextOutputFormat.class);

//        设置输入目录
        FileInputFormat.addInputPath(job,new Path(args[0]));
        Path outputPath = new Path(args[1]);

//        设置输出目录
        FileOutputFormat.setOutputPath(job,outputPath);

//        提交任务
        boolean isSuccess = job.waitForCompletion(true);

        return isSuccess ?0:1;

//
    }


    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new Distinct(),args));
    }
}
