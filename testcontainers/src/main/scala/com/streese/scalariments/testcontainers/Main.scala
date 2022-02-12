package com.streese.scalariments.testcontainers

import com.streese.scalariments.testcontainers.containers
import org.testcontainers.containers.Network
import org.testcontainers.lifecycle.Startables

import scala.io.StdIn._
import scala.jdk.FutureConverters._

object Main extends App {

  val network = Network.newNetwork()

  val kafka = containers.kafka[Nothing](network)
  val schemaRegistry = containers.schemaRegistry[Nothing](network, kafka)

  Startables.deepStart(kafka, schemaRegistry).asScala

  println("waiting for input")
  readLine()
  println("exiting")

}
