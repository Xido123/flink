package test



import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
case class SensorReading( id:String,timestamp:Long,temperature:Double){}
object TransformTest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val inputPath = "C:\\Users\\zmlxd\\Desktop\\xido\\note\\flink\\code\\src\\main\\scala\\test\\sensor.txt"
    val inputStream = env.readTextFile(inputPath)
//1    先转换成样例类类型
    val dataStream = inputStream
      .map(data=>{
        val arr = data.split(",")
        SensorReading(arr(0).trim,arr(1).trim.toLong,arr(2).trim.toDouble)
      })
// 2 分组聚合   输出每个传感器当前最小值

//    val aggStream  = dataStream
//      .keyBy("id")
//      .minBy("temperature")


    /*多流转换
    * 分成低温高温两个流
    * */

    val splitStream = dataStream
      .split(data=>{
        if(data.temperature > 30.0) Seq("high") else Seq("low")
      })
      val highTempStream = splitStream.select("high")
      val lowTempStream = splitStream.select("low")
//      val allTempStream = splitStream.select("high","low")
//    aggStream.print()
      highTempStream.print("high")
      lowTempStream.print("low")
//      allTempStream.print("all")
      val warningStream = highTempStream.map(data =>(data.id,data.temperature))
      val connectedStreams = warningStream.connect(lowTempStream)

//      用coMap对数据进行分别处理
      val coMapResultStream = connectedStreams
        .map(waringData=>(waringData._1,waringData._2,"waring"),
          lowTempStream=>(lowTempStream.id,"healthy")
        )

        coMapResultStream.print("comap")

        env.execute("start")

  }
}
