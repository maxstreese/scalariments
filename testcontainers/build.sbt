import dependencies._

ThisBuild / organization := "com.streese.scalariments"
ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version      := "0.1.0-SNAPSHOT"

ThisBuild / resolvers ++= Seq(
  "Confluent" at "https://packages.confluent.io/maven/"
)

ThisBuild / scalacOptions ++= List(
  "-Xlint:unused",
  "-Ywarn-macros:after",
  "-Ywarn-unused"
)

lazy val scalarimentsTestcontainers = (project in file("."))
  .settings(
    name                 := "scalariments-testcontainers",
    libraryDependencies ++= all
  )
