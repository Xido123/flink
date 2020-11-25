import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import java.io.IOException;

/**前两个泛型为MR框架给mapper程序的输入，输入的对象为一行数据对象，<行首的偏移量，行的内容>
 * 例如需要处理的源文件为    hello hadoop
 *                       hello flink
 *则此时mapper需要处理的第一个数据对象为<0,"hello hadoop">， 0对应LongWritable，“hello hadoop“对应 Text
 *处理完第一个再重复处理第二个。
 *
 * @author xido
*/
public class WcMapper extends Mapper<LongWritable,Text, Text, IntWritable> {
    private Text word = new Text();
    private IntWritable one = new IntWritable(1);

    @Override
//    重写父类的map方法
//    Context对象为当前整个mapreduce任务给map开的一个输入输出接口，map从Context读取输入处理完输出给Context
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

//      拿到这一行数据
        String line = value.toString();

//      按照空格切分数据装入一个数组容器
        String[] words = line.split(" ");

//        for(int i = 0;i<13;i++){
//            this.word.set(words[i]);
//            context.write(this.word,this.one);
//        }

//      遍历数组，把单词编程（word，1）的形式交给Context
        for (String word:words) {
//          原为context.write(new Text(word),new IntWritable(1));
//          由于大量new对象浪费资源，所以定义了成员变量
            this.word.set(word);
            context.write(this.word,this.one);



        }
    }
}
