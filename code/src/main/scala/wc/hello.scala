package wc

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object hello {
  def main(args: Array[String]): Unit = {
    val env:ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val streamEnv:StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val envCreate:StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment(1)
  }

}
