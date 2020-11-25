/**
 * MaxWord.java
 * com.hainiu.hainiumr.day01
 * Copyright (c) 2020, 海牛版权所有.
 * @author   潘牛                      
*/

package day4;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import day4.BaseMR;

/**
 * 求数值最大的单词和数值（求极值）
 * @author   潘牛                      
 * @Date	 2020年10月20日 	 
 */
public class MaxWord extends BaseMR{
	
	public static class MaxWordMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		
		/**
		 * map输出的key：最大值对应的单词
		 */
		Text keyOut = new Text();
		
		/**
		 * map输出的value：当前map的最大值
		 */
		LongWritable valueOut = new LongWritable();
		
		long maxNum = Integer.MIN_VALUE;
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] arr = line.split("\t");
			
			if(arr.length != 2){
				context.getCounter("hainiu", "bad line num").increment(1L);
				return;
			}
			
			String word = arr[0];
			long num = Long.parseLong(arr[1]);
			
			if(num > maxNum){
				maxNum = num;
				keyOut.set(word);
			}
			
		}
		
		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {
			valueOut.set(maxNum);
			context.write(keyOut, valueOut);
			System.out.println("mapper output=> " + keyOut + ", " + valueOut.get());
			
		}
	}
	

	public static class MaxWordReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
		
		/**
		 * 全局最大值对应的单词
		 */
		Text keyOut = new Text();
		
		/**
		 * 全局最大值
		 */
		LongWritable valueOut = new LongWritable();
		
		long maxNum = Integer.MIN_VALUE;
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,
				Context arg2) throws IOException, InterruptedException {
			// key: wanglei1 
			// values : [700]
			for(LongWritable w : values){
				long num = w.get();
				if(num > maxNum){
					maxNum = num;
					keyOut.set(key.toString());
				}
			}
			
		}
		
		@Override
		protected void cleanup(Context context)
				throws IOException, InterruptedException {
			valueOut.set(maxNum);
			context.write(keyOut, valueOut);
			System.out.println("reducer output=> " + keyOut + ", " + valueOut.get());
		}
	}


	@Override
	protected Job getJob(Configuration conf) throws Exception {
		// -------创建job对象-------
		Job job = Job.getInstance(conf, getJobNameWithTaskId());
		
		// -------job对象参数设置-------
		// 后面部署集群运行时能用到
		job.setJarByClass(MaxWord.class);
		
		// 设置job任务运行的mapper类
		job.setMapperClass(MaxWordMapper.class);
		// 设置job任务运行的reducer类
		job.setReducerClass(MaxWordReducer.class);
		
		
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
		
		
		// maxword的输入目录是wordcount的输出目录
		// 需要创建wordcount对象，通过wordcount对象的方法获取wordcount的输出目录
		WordCount wc = new WordCount();
		
		FileInputFormat.addInputPath(job, wc.getJobOutputPath(wc.getJobNameWithTaskId()));
		
		// 设置输出目录
		FileOutputFormat.setOutputPath(job, getJobOutputPath(getJobNameWithTaskId()));
		
		return job;
		
	}


	@Override
	protected String getJobName() {
	
		return this.getClass().getName();
		
	}

}

