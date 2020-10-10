package wc

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

//批处理命令的wordcount
//

object WordCount {
  def main(args: Array[String]): Unit = {
    var env:ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
//      然后这里需要说的是操作时需要引入一个apisacla
//    把scala包的隐式转换引入 接下来执行环境首先得出数据
//    从文件中读取数据
//
  val inputPath:String = "C:\\Users\\zmlxd\\Desktop\\xido\\note\\flink\\code\\src\\main\\resources\\hello.txt"
  val inputDataSet:DataSet[String] = env.readTextFile(inputPath)

// 对数据进行转换处理统计 先分词 打散 分组聚合统计 熟悉的操作
//
    val resultDataSet:AggregateDataSet[(String, Int)] = inputDataSet.flatMap(_.split(" ")).map((_,1)).groupBy(0)
  .sum(1)
//    对当前所有分组第二个打印求和直接叫做print

//的第二个元素进行求和 打印输出 result dataset

    resultDataSet.print()
//代码执行完毕 非常符合我们的预期 hello 比较多
//               彼得你爹
//    只出现了一次每一个都统计了出来【得
//    批处理不需要关闭操作 有界集  所以 对于批处理 非常相似界集n

//    第一个元素进行分组

  }

}
