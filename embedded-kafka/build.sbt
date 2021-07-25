ThisBuild / organization := "com.streese"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / version      := "0.1.0-SNAPSHOT"

ThisBuild / resolvers       ++= Seq(
  "confluent" at "https://packages.confluent.io/maven/",
  "jitpack" at "https://jitpack.io"
)

ThisBuild / dockerBaseImage := "openjdk:8-jre-buster"

lazy val embeddedKafka = (project in file("."))
  .aggregate(producer, kafka)
  .settings(
    name           := "embedded-kafka",
    publish / skip := true
  )

lazy val producer = (project in file("producer"))
  .enablePlugins(AshScriptPlugin, DockerPlugin, JavaAppPackaging)
  .settings(
    name                  := "embedded-kafka-producer",
    libraryDependencies  ++= Seq(libLogback, libRegistravka4sKafka, libPureConfig, libScalaLogging)
  )

lazy val kafka = (project in file("kafka"))
  .enablePlugins(AshScriptPlugin, DockerPlugin, JavaAppPackaging)
  .settings(
    name                  := "embedded-kafka-kafka",
    libraryDependencies  ++= Seq(libEmbeddedKafka, libLogback, libPureConfig, libScalaLogging)
  )


lazy val libEmbeddedKafka      = "io.github.embeddedkafka"    %% "embedded-kafka-schema-registry" % "6.2.0"
lazy val libLogback            = "ch.qos.logback"              % "logback-classic"                % "1.2.4"
lazy val libRegistravka4sKafka = "com.streese.registravka4s"  %% "registravka4s-kafka"            % "0.4.0"
lazy val libPureConfig         = "com.github.pureconfig"      %% "pureconfig"                     % "0.16.0"
lazy val libScalaLogging       = "com.typesafe.scala-logging" %% "scala-logging"                  % "3.9.4"

ThisBuild / scapegoatVersion := "1.4.9"
