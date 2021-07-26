package com.streese.embedded.kafka.producer

import pureconfig._
import pureconfig.generic.auto._

case class Config(
  schemaRegistryUri: String,
  kafkaBootstrapUri: String,
  kafkaTopic:        String
)

object Config {
  def apply(): Config = ConfigSource.default.loadOrThrow[Config]
}
