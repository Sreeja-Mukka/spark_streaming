import org.apache.spark.sql.SparkSession
import java.io.{File, PrintWriter}

object ProcessCsv extends App {
  // Initialize Spark Session
  val spark = SparkSession.builder()
    .appName("CSV Folder Processor")
    .master("local[*]") 
    .config("spark.ui.port", "4080")
    .getOrCreate()

  def getListOfSubDirectories(directoryName: String): Array[String] = {
    new File(directoryName)
      .listFiles
      .filter(_.isDirectory)
      .filter(dir => !dir.getName.contains("_spark_metadata"))
      .map(_.getPath)
  }

  def processFolder(folderPath: String): Unit = {
    println(s"Processing folder: $folderPath")
   
    val df = spark.read
      .option("header", "false") // Specifying that files do not contain headers
      .option("inferSchema", "true")
      .csv(s"$folderPath/*.csv")
      

    val recordCount = df.count()

    val writer = new PrintWriter(new File(s"$folderPath/count.txt"))
    try {
      writer.write(s"Total records: $recordCount\n")
    } finally {
      writer.close()
    }
  }

  val baseDirectory = "/Users/smukka/Desktop/csv_files" 

  val subFolders = getListOfSubDirectories(baseDirectory)

  subFolders.foreach(processFolder)
  spark.stop()
}
