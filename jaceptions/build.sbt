ThisBuild / organization := "com.streese.scalariments"
ThisBuild / scalaVersion := "3.1.1"
ThisBuild / version      := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .aggregate(backend, jlib)
  .settings(
    name           := "jaceptions",
    publish / skip := true
  )

lazy val backend = (project in file("backend"))
  .dependsOn(jlib)
  .settings(
    name := "jaceptions-backend"
  )

lazy val jlib = (project in file("jlib"))
  .settings(
    name := "jaceptions-jlib"
  )
