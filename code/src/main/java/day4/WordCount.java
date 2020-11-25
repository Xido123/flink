/**
 * WordCount.java
 * com.hainiu.hainiumr.day01
 * Copyright (c) 2020, 海牛版权所有.
 * @author   潘牛                      
*/

package day4;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import day4.BaseMR;

/**
 * 根据图实现单词统计
 * @author   潘牛                      
 * @Date	 2020年10月20日 	 
 */
public class WordCount extends BaseMR{
	
	public static class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		
		/**
		 * map输出的key：单词
		 */
		Text keyOut = new Text();
		
		/**
		 * map输出的value:1
		 */
		LongWritable valueOut = new LongWritable(1);
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			// context: 连接mapreduce的上下文环境
			// one world
			String line = value.toString();
			// {one, world}
			String[] arr = line.split(" ");
			
			for(String word : arr){
				// 把数据放到Text
				keyOut.set(word);
				// 通过write 方法，把k,v 数据输出出去
				context.write(keyOut, valueOut);
				System.out.println("mapper output ==> keyout:" + word + ", valueout:" + valueOut.get());
			}
			
		}
	}
	
	/*
	 * reduce 的 KEYIN, VALUEIN 和 mapper阶段输出的KEYOUT, VALUEOUT 类型一致
	 * KEYOUT, VALUEOUT 取决于业务最终要输出啥
	 * 本例要实现单词统计，所以
	 * KEYOUT： 单词   --> Text
	 * VALUEOUT： 统计结果，数值  --> LongWritable 
	 */
	public static class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
		
		/**
		 * map输出的value: 数值
		 */
		LongWritable valueOut = new LongWritable();
		
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,Context context) throws IOException, InterruptedException {
			
			// one,   [1,1,1,1]   ---> one, 4
			long sum = 0L;
			StringBuilder sb = new StringBuilder("reduce input ==>key:" + key.toString());
			sb.append(", values:[");
			for(LongWritable w : values){
				long num = w.get();
				sb.append(num).append(",");
				sum += num;
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
			System.out.println(sb.toString());
			
			valueOut.set(sum);
			context.write(key, valueOut);
			
			
		}
		
	}

	@Override
	protected Job getJob(Configuration conf) throws Exception {
		
		conf.set(FileOutputFormat.COMPRESS, "true");
		conf.set(FileOutputFormat.COMPRESS_CODEC, GzipCodec.class.getName());
		
		
		// -------创建job对象-------
		Job job = Job.getInstance(conf, getJobNameWithTaskId());
		
		
		// -------job对象参数设置-------
		
		job.setJarByClass(WordCount.class);
		
		// 设置job任务运行的mapper类
		job.setMapperClass(WordCountMapper.class);
		// 设置job任务运行的reducer类
		job.setReducerClass(WordCountReducer.class);
		
		// 设置两个reduce，如果不设置默认是1
//		job.setNumReduceTasks(2);
		
		// 设置job任务运行的mapper类keyout
		job.setMapOutputKeyClass(Text.class);
		
		// 设置job任务运行的mapper类valueout
		job.setMapOutputValueClass(LongWritable.class);
		
		// 设置job任务运行的reducer类keyout
		job.setOutputKeyClass(Text.class);
		// 设置job任务运行的reducer类valueout
		job.setOutputValueClass(LongWritable.class);
		
		// 设置job任务运行的inputformatclass 【如果是文件文件，可以不设置，但其他格式文件，必须要设置】
		job.setInputFormatClass(TextInputFormat.class);
		
		// 设置job任务运行的outputformatclass【如果是文件文件，可以不设置，但其他格式文件，必须要设置】
		job.setOutputValueClass(TextOutputFormat.class);
		
		
		// 设置输入目录
		FileInputFormat.addInputPath(job, getFirstJobInputPath());
	

		// 设置输出目录
		FileOutputFormat.setOutputPath(job, getJobOutputPath(getJobNameWithTaskId()));
		
		return job;
		
	}

	@Override
	protected String getJobName() {
		
		return this.getClass().getName();
		
	}
	

}

