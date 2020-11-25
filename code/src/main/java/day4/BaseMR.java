/**
 * BaseMR.java
 * com.hainiu.hainiumr.mrrun.base
 * Copyright (c) 2020, 海牛版权所有.
 * @author   潘牛                      
*/

package day4;

import day4.util.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;

import java.util.Map.Entry;

/**
 * 公共基类，提供一些通用方法
 * @author   潘牛                      
 * @Date	 2020年10月27日 	 
 */
public abstract class BaseMR {

	public static Configuration conf = new Configuration();
	
	public void setConf(Configuration conf){
		BaseMR.conf = conf;
	}
	
	
	/**
	 * 定义获取ControlledJob对象，内部会根据子类对象去实现获取相应子类的Job对象并与
	 * ControlledJob对象关联
	*/
	public ControlledJob getControlledJob()throws Exception{
		//删除输出目录【个性化】
		// 自动删除mapreduce的输出目录
		FileSystem fs = FileSystem.get(conf);
		// 获取个性化的输出目录
		Path outputPath = getJobOutputPath(getJobNameWithTaskId());
		if(fs.exists(outputPath)){
			// 递归删除输出目录
			fs.delete(outputPath, true);
			System.out.println("outputpath:【" + outputPath.toString() + "】 delete success!");
		}
		
		//创建ControlledJob对象
		ControlledJob cJob = new ControlledJob(conf);
		
		// 为了避免公共的conf对象被改动，需要复制新的conf对象传入getJob()中
		Configuration jobConf = new Configuration();
		for(Entry<String,String> entry : conf){
			String key = entry.getKey();
			String value = entry.getValue();
			jobConf.set(key, value);
		}
		
		//获取job对象，由子类去实现【个性化】
		Job job = getJob(jobConf);
		
		//设置job和ControlledJob对象的关系
		cJob.setJob(job);
		return cJob;
	}

	/**
	 * 定义抽象方法<br/>
	 * 用于子类个性化实现
	*/
	abstract protected Job getJob(Configuration conf)throws Exception;

	/**
	 * 每个任务有最基本的名称： 如：wordcount<br/>
	 * 抽象方法，需要子类实现具体名称
	*/
	abstract protected String getJobName(); 

	/**
	 * 每个任务可以有个性化的任务名称：  wordcount_0329_panniu <br/>
	 * 用-D参数传递 task.id=0329_panniu
	*/
	public String getJobNameWithTaskId(){
		return getJobName() + "_" + conf.get(Constants.TASK_ID_ATTR);  
	}

    /**
     * 首个任务的输入目录： 用-D参数传递 task.input.dir=/tmp/mr/task/input
    */
    public Path getFirstJobInputPath(){
		return new Path(conf.get(Constants.TASK_INPUT_DIR_ATTR));
	}

	/**
	 * basepath：/tmp/mr/task   用-D参数传递   task.base.dir=/tmp/mr/task<br/>
	 * 个性化任务名称：wordcount_0329_panniu  --->  getJobName() + "_" + conf.get("task.id");<br/>
	 * conf.get("task.base.dir")/getJobName() + "_" + conf.get("task.id");
	 * 
	 * 以wordcount为例：
	 *  /tmp/mr/task/wordcount  --> getJobOutputPath(getJobName)
	 *  /tmp/mr/task/wordcount_0329_panniu --> getJobOutputPath(getJobNameWithTaskId())
	*/
	public Path getJobOutputPath(String jobName){
		return new Path(conf.get(Constants.TASK_BASE_DIR_ATTR) + "/" + jobName);
	}
	

}

