package wc

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._


object StreamWordCount {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    hash分配 都是2 都是3 而且只要是haell 都在当前的线程上 要求 主要
//    做了keyby
//     基于某个key做了分组之后并行之后一很多个并行的任务比方说 sum 上又 map的结果
//    如果说豆芽哦做sun基于hashcode 大可去提供的并行任务类似于驱魔区域处理过车最有的大偶一个档期啊你并行子任务的编号hash雨伞总会分配人道通过一个子任务中公去
//    有可能分发同一个hash取余之后得到多少 5里又fib 理解的线程中并行任务同一个word总是在同一个档当前的并行任务以及对于u但Gina数得出额
//
//    非常丰富灵活的设置并行度的递延
//    从外部命令种提取参数主机名和端口号 提取的方式

    val paramTool:ParameterTool  = ParameterTool.fromArgs(args)
    val host:String = paramTool.get("host")
    var port:Int = paramTool.getInt("port")
    val inputDataStream:DataStream[String] = env.socketTextStream(host,port)

    val resultDataStream:DataStream[(String,Int)]  = inputDataStream
      .flatMap(_.split(" "))
      .filter(_.nonEmpty)
      .map((_,1))
      .keyBy(0)
      .sum(1)

    resultDataStream.print().setParallelism(1)
//启动一个进程 用之前计算好的每一步操作启动任务执行调用环境  流处理执行环境的
//    ex花鸟卷 加这么一句 给参数 的参数美哟u也可以不给
//    当前执行的job坐月流失的
    env.execute("www")

  }

}
