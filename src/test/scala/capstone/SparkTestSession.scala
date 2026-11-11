package capstone

import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, Suite}

trait SparkTestSession extends BeforeAndAfterAll {

  this: Suite =>

  protected lazy val spark: SparkSession =
    SparkSession
      .builder()
      .appName("SparkTestingCapstone")
      .master("local[2]")
      .config("spark.ui.enabled", "false")
      .getOrCreate()

  override protected def afterAll(): Unit = {
    try {
      spark.stop()
    } finally {
      super.afterAll()
    }
  }
}
