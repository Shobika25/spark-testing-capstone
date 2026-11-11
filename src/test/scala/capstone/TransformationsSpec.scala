package capstone

import org.scalatest.funsuite.AnyFunSuite
import org.apache.spark.sql.SparkSession

class TransformationsSpec extends AnyFunSuite {

  private val spark = SparkSession.builder()
    .appName("TransformationsSpec")
    .master("local[2]")
    .config("spark.ui.enabled", "false")
    .getOrCreate()

  import spark.implicits._

  test("cleanOrders removes invalid rows and normalizes columns") {

    val input = Seq(
      ("O-1", "C001", " PAID "),
      ("O-2", "C002", "PENDING"),
      ("O-3", "   ", "PAID"),
      (null, "C004", "PAID")
    ).toDF("order_id", "customer_id", "status")

    val actual = Transformations.cleanOrders(input)

    actual.show(false)
  }
}