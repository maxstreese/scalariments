package com.streese.embedded.kafka.kafka

import io.github.embeddedkafka.schemaregistry.EmbeddedKafka
import io.github.embeddedkafka.schemaregistry.EmbeddedKafkaConfig

object Main extends App with EmbeddedKafka {

  implicit val config = EmbeddedKafkaConfig(
    kafkaPort          = 9092,
    zooKeeperPort      = 2821,
    schemaRegistryPort = 8080
  )

  val server = EmbeddedKafka.start()

  createCustomTopic(topic = "ticks", partitions = 1)

  server.broker.awaitShutdown()

}
