package capstone

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object DataQuality {

  def rowCountIs(df: DataFrame, expected: Long): Boolean =
    df.count() == expected

  def nullCount(df: DataFrame, columnName: String): Long =
    df.filter(col(columnName).isNull).count()

  def hasNoNulls(df: DataFrame, columnName: String): Boolean =
    nullCount(df, columnName) == 0L

  def duplicateCount(df: DataFrame, keyColumns: Seq[String]): Long = {
    require(keyColumns.nonEmpty, "At least one key column is required")
    df.groupBy(keyColumns.map(col): _*)
      .count()
      .filter(col("count") > 1)
      .count()
  }

  def isUnique(df: DataFrame, keyColumns: Seq[String]): Boolean =
    duplicateCount(df, keyColumns) == 0L

  def hasNulls(df: DataFrame, columnName: String): Boolean = {

    require(
      df.columns.contains(columnName),
      s"Column does not exist: $columnName"
    )

    df.filter(col(columnName).isNull)
      .limit(1)
      .count() > 0
  }
}
