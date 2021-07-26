package com.streese.embedded.kafka.kafka

import pureconfig._
import pureconfig.generic.auto._

case class Config(
  kafkaPort:                        Int,
  zooKeeperPort:                    Int,
  schemaRegistryPort:               Int,
  kafkaListeners:                   String,
  kafkaAdvertisedListeners:         String,
  kafkaListenerSecurityProtocolMap: String
)

object Config {
  def apply(): Config = ConfigSource.default.loadOrThrow[Config]
}
