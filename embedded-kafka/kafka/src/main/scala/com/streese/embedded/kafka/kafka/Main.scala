package com.streese.embedded.kafka.kafka

import io.github.embeddedkafka.schemaregistry.EmbeddedKafka
import io.github.embeddedkafka.schemaregistry.EmbeddedKafkaConfig

object Main extends App with EmbeddedKafka {

  val config = Config()

  implicit val kafkaConfig = EmbeddedKafkaConfig(
    kafkaPort              = config.kafkaPort,
    zooKeeperPort          = config.zooKeeperPort,
    schemaRegistryPort     = config.schemaRegistryPort,
    customBrokerProperties = Map(
      "listeners"                      -> config.kafkaListeners,
      "advertised.listeners"           -> config.kafkaAdvertisedListeners,
      "listener.security.protocol.map" -> config.kafkaListenerSecurityProtocolMap
    )
  )

  val server = EmbeddedKafka.start()

  createCustomTopic(topic = "ticks", partitions = 1)

  server.broker.awaitShutdown()

}
