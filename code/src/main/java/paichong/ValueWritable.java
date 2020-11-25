package paichong;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ValueWritable implements Writable {

//    存放单词hadoop自带的单词，
    private Text wordT = new Text();

//    存放数值
    private long num = 0L;
    public void write(DataOutput out) throws IOException {
//        序列化
        out.writeUTF(wordT.toString());
        out.writeLong(num);
    }
//反序列化
    public void readFields(DataInput in) throws IOException {
        String word = in.readUTF();
        wordT.set(word);
        num = in.readLong();
    }

    public void setWordT(Text wordT) {
        this.wordT = wordT;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public Text getWordT() {
        return wordT;
    }

    public long getNum() {
        return num;
    }
}
