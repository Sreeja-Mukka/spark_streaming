scalaVersion := "2.12.19"

name := "spark-streaming"
organization := "ch.epfl.scala"
version := "1.0"

fork in run := true
libraryDependencies ++= Seq(
 "com.typesafe.akka" %% "akka-actor-typed" % "2.6.15",
  "com.typesafe.akka" %% "akka-http" % "10.2.6",
  "com.typesafe.akka" %% "akka-stream" % "2.6.15",
  "org.apache.kafka" %% "kafka" % "2.8.0",
  "com.typesafe.akka" %% "akka-slf4j" % "2.6.15",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  
  "io.spray" %% "spray-json" % "1.3.6",

  "org.apache.spark" %% "spark-core" % "3.1.1" ,
  "org.apache.spark" %% "spark-sql" % "3.1.1" ,
  "com.google.protobuf" % "protobuf-java" % "4.27.0",

  "org.apache.spark" %% "spark-streaming" % "3.1.1",
"org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.1.1",
"org.apache.spark" %% "spark-sql-kafka-0-10" % "3.1.1",

"com.thesamet.scalapb" %% "compilerplugin" % "0.11.4"
)
dependencyOverrides += "com.google.guava" % "guava" % "27.0-jre"

import sbtassembly.AssemblyPlugin.autoImport._

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => xs match {
    case "MANIFEST.MF" :: Nil => MergeStrategy.discard // Custom strategy as an example
    case "module-info.class" :: Nil => MergeStrategy.concat
    case _ => MergeStrategy.discard // Or use other strategies as necessary
  }
  case "reference.conf" => MergeStrategy.concat
  case "application.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}

resolvers ++= Seq(
    Resolver.mavenLocal,
  Resolver.jcenterRepo,
   "spray repo" at "https://repo.spray.io/",
  Resolver.bintrayRepo("hseeberger", "maven"),
  "Apache Snapshots" at "https://repository.apache.org/snapshots/"
)

javaOptions ++= Seq(
  "--add-opens=java.base/java.nio=ALL-UNNAMED",
  "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
  "--add-opens=java.base/java.lang=ALL-UNNAMED"
)
