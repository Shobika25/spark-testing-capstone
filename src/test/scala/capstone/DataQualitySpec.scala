package capstone

import org.scalatest.funsuite.AnyFunSuite
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._

class DataQualitySpec extends AnyFunSuite with SparkTestSession {

  test("checks row count") {

    val df = spark.range(1, 4).toDF("id")

    assert(DataQuality.rowCountIs(df, 3))
    assert(!DataQuality.rowCountIs(df, 4))
  }

  test("detects null values") {

    // Explicit schema prevents Spark from inferring null as Any
    val schema = StructType(
      Seq(
        StructField("name", StringType, nullable = true),
        StructField("age", IntegerType, nullable = true)
      )
    )

    val data = Seq(
      Row("Rajesh", 35),
      Row("Kumar", null)
    )

    val df = spark.createDataFrame(
      spark.sparkContext.parallelize(data),
      schema
    )

    assert(DataQuality.hasNulls(df, "age"))
    assert(!DataQuality.hasNulls(df, "name"))
  }
}
