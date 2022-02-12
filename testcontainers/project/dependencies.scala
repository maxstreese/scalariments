import sbt._
import Keys._

object dependencies {

  private object versions {
    val testcontainers = "1.16.2"
  }

  private val testcontainersScala = "org.testcontainers" % "testcontainers" % versions.testcontainers

  val all: Seq[ModuleID] = Seq(testcontainersScala)

}
