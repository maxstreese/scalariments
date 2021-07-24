ThisBuild / organization := "com.streese"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / version      := "0.1.0-SNAPSHOT"

lazy val akkaDebezium = (project in file("."))
  .settings(
    name := "akka-debezium",
    libraryDependencies ++= Seq(
      // Runtime
      libDebeziumApi,
      libDebeziumEmbedded,
      libDebeziumPostgres,
      libFlywayCore,
      libLogback,
      libPostgreSql,
      libPureConfig,
      libScalaLogging,
      // Test
      libScalaCheck,
      libScalaTest
    )
  )

lazy val libDebeziumApi      = "io.debezium"                 % "debezium-api"                % "1.6.1.Final"
lazy val libDebeziumEmbedded = "io.debezium"                 % "debezium-embedded"           % "1.6.1.Final"
lazy val libDebeziumPostgres = "io.debezium"                 % "debezium-connector-postgres" % "1.6.1.Final"
lazy val libFlywayCore       = "org.flywaydb"                % "flyway-core"                 % "7.11.3"
lazy val libLogback          = "ch.qos.logback"              % "logback-classic"             % "1.2.4"
lazy val libPostgreSql       = "org.postgresql"              % "postgresql"                  % "42.2.23"
lazy val libPureConfig       = "com.github.pureconfig"      %% "pureconfig"                  % "0.16.0"
lazy val libScalaLogging     = "com.typesafe.scala-logging" %% "scala-logging"               % "3.9.4"

lazy val libScalaCheck = "org.scalacheck" %% "scalacheck" % "1.15.4" % "test"
lazy val libScalaTest  = "org.scalatest"  %% "scalatest"  % "3.2.9"  % "test"

ThisBuild / scapegoatVersion := "1.4.9"
