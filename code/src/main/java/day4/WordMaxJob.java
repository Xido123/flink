/**
 * WordMaxJob.java
 * com.hainiu.hainiumr.day04
 * Copyright (c) 2020, 海牛版权所有.
 * @author   潘牛                      
*/

package day4;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 定义mapreduce的任务工作链
 * wordcount  --->  maxword
 * @author   潘牛                      
 * @Date	 2020年10月24日 	 
 */
public class WordMaxJob extends Configured implements Tool{

	@Override
	public int run(String[] args) throws Exception {
		// 获取configuration对象
		Configuration conf = this.getConf();
		
		// 创建任务链对象（是个线程对象）
		final JobControl jobc = new JobControl("WordMaxJob");
		
		WordCount wc = new WordCount();
		// 第一个任务设置一次conf给 BaseMR类的Conf，这样后面的所有对象都可以用
		wc.setConf(conf);
		ControlledJob wcjob = wc.getControlledJob();
		
		MaxWord mw = new MaxWord();
		ControlledJob mwjob = mw.getControlledJob();
		
		
		// 设置Job间依赖关系
		mwjob.addDependingJob(wcjob);
		
		// 把ControlledJob对象添加到任务链上
		jobc.addJob(wcjob);
		jobc.addJob(mwjob);
		
		
		// 开启监控任务链的线程，发现任务链任务都完成了，就停止任务链，任务链状态改变，下面运行就是不是死循环了
		Thread t = new Thread() {
			@Override
			public void run() {
				
				// 开始时间
				long startTime = System.currentTimeMillis();
				// 循环判断任务链的任务是否都完成了
				while(!jobc.allFinished()){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
				}
				
				// 结束时间
				long endTime = System.currentTimeMillis();
				
				// 运行时长(秒)
				long runTime = (endTime - startTime) / 1000;
				
				System.out.println("任务链运行时长：" + runTime + " 秒");
				
				// 获取失败的job列表
				List<ControlledJob> failedJobList = jobc.getFailedJobList();
				if(failedJobList.size() == 0){
					System.out.println("任务链执行 SUCCESS");
				}else{
					System.out.println("任务链执行 FAIL,失败任务名称如下：");
					for(ControlledJob cjob : failedJobList){
						System.out.println("\t" + cjob.getJobName());
					}
				}

				// 停止任务链
				jobc.stop();
			
				
			}
		};
		t.start();
		
		// 运行任务链
		// 因为job.run() 是个死循环，如果不更改任务链状态，那就是死循环
		jobc.run();
		
		return 0;
		
	}


	public static void main(String[] args) throws Exception {
		// -Dtask.id=1017_panniu -Dtask.input.dir=/tmp/mr/task/input -Dtask.base.dir=/tmp/mr/task
		System.exit(ToolRunner.run(new WordMaxJob(), args));
	}

}

