package paichong;

import org.apache.flink.shaded.guava18.com.google.common.collect.Table;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

import java.io.IOException;

public class MaxWord2 extends Configured implements Tool {

    public static class MaxWord2ValueWMapepr extends Mapper<LongWritable, Text,Text,LongWritable>{
        Text keyOutput = new Text("max");

        ValueWritable valueOut = new ValueWritable();

        long maxNum =  Long.MAX_VALUE;

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String arr[] = line.split("\t");
//            防御式编程
            String word = arr[0];
            long num = Long.parseLong(arr[1]);

//            通过这样的方式获取最大值和单词
            if(maxNum <num){
                maxNum = num;
                valueOut.setWordT(new Text(word));
                valueOut.setNum(maxNum);
            }


        }

        @Override
        protected void cleanup(Context context){

        }
    }



    public int run(String[] args) throws Exception {
        return 0;
    }
}
