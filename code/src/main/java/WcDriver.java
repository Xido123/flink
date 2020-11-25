import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import scala.collection.parallel.Combiner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WcDriver {

    /*
    *前面只是定义了map与reduce的逻辑
    * 但是没有接入MR框架
    *在Driver中配置map与reduce的环境（输入文件输出文件等）
    */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

//     1 获取一个job实例 job即一个mapreduce task
//        加载公共配置 加载mapreduce 加载配置也是static中
//        加括号加空格者是 静态块类初始化的时候这已经初始化已经初始化了执行这句话化的
//        第一次调用这已经初始化可
//        yarn-site
//        运行mapreduce所需要的配置文件 公共配置 map的配置玉兴
//        下边这些
               Job job =  Job.getInstance(new Configuration());


//     2 设置Driver类路径
        job.setJarByClass(WcDriver.class);

//      3 设置mapper与reducer
        job.setMapperClass(WcMapper.class);
        job.setReducerClass(WcReducer.class);

//      4 设置Mapper与Reducer输出的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

//      设置reducer即总输出
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

//      5  设置输入输出数据存储的文件夹
//         Path由main指定
        FileInputFormat.setInputPaths(job, new Path("input.txt"));
        FileOutputFormat.setOutputPath(job,new Path("out.txt"));


//      6 提交我们的job


        boolean b= job.waitForCompletion(true);
        System.exit(b?0:1);
    }
}
