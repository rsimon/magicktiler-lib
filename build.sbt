organization := "at.ait.dme"

name := "magicktiler-lib"

version := "0.1.0"

scalaVersion := "2.10.4"

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-parser-combinators" % "2.11.0-M4",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5",
  "log4j" % "log4j" % "1.2.17",
  "org.im4java" % "im4java" % "1.4.0",
  "com.thoughtworks.xstream" % "xstream" % "1.4.7",
  "junit" % "junit" % "4.11" % "test"
)
