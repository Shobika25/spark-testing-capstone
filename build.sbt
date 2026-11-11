
import sbtassembly.AssemblyPlugin.autoImport._
import sbtassembly.MergeStrategy

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "spark-testing-capstone",

    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "3.5.1",
      "org.scalatest" %% "scalatest" % "3.2.18" % Test
    ),

    Test / fork := true,

    Test / javaOptions ++= Seq(
      "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED"
    ),

    Test / parallelExecution := false,

    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", "versions", _ @ _*) =>
        MergeStrategy.discard

      case PathList("META-INF", _ @ _*) =>
        MergeStrategy.discard

      case "reference.conf" =>
        MergeStrategy.concat

      case _ =>
        MergeStrategy.first
    }
  )
