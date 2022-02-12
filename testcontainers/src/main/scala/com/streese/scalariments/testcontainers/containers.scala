package com.streese.scalariments.testcontainers

import org.testcontainers.containers.FixedHostPortGenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.lifecycle.Startable
import org.testcontainers.utility.MountableFile

import scala.jdk.CollectionConverters._

object containers {

  def kafka[T <: FixedHostPortGenericContainer[T]](network: Network): FixedHostPortGenericContainer[T] =
    new FixedHostPortGenericContainer[T]("confluentinc/cp-kafka:7.0.1")
      .withNetwork(network)
      .withFixedExposedPort(9092, 9092)
      .withFixedExposedPort(9101, 9101)
      .withCreateContainerCmdModifier(_.withHostName("broker"))
      .withNetworkAliases("broker")
      .withEnv(Map(
        "KAFKA_BROKER_ID"                                -> "1",
        "KAFKA_LISTENER_SECURITY_PROTOCOL_MAP"           -> "CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT",
        "KAFKA_ADVERTISED_LISTENERS"                     -> "PLAINTEXT://broker:29092,PLAINTEXT_HOST://localhost:9092",
        "KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR"         -> "1",
        "KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS"         -> "0",
        "KAFKA_TRANSACTION_STATE_LOG_MIN_ISR"            -> "1",
        "KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR" -> "1",
        "KAFKA_JMX_PORT"                                 -> "9101",
        "KAFKA_JMX_HOSTNAME"                             -> "localhost",
        "KAFKA_PROCESS_ROLES"                            -> "broker,controller",
        "KAFKA_NODE_ID"                                  -> "1",
        "KAFKA_CONTROLLER_QUORUM_VOTERS"                 -> "1@broker:29093",
        "KAFKA_LISTENERS"                                -> "PLAINTEXT://broker:29092,CONTROLLER://broker:29093,PLAINTEXT_HOST://0.0.0.0:9092",
        "KAFKA_INTER_BROKER_LISTENER_NAME"               -> "PLAINTEXT",
        "KAFKA_CONTROLLER_LISTENER_NAMES"                -> "CONTROLLER",
        "KAFKA_LOG_DIRS"                                 -> "/tmp/kraft-combined-logs",
      ).asJava)
      .withCopyFileToContainer(MountableFile.forClasspathResource("update_run.sh", 511), "/tmp/update_run.sh")
      .withCommand("bash", "-c", "/tmp/update_run.sh && /etc/confluent/docker/run")

  def schemaRegistry[T <: FixedHostPortGenericContainer[T]](network: Network, kafka: Startable): FixedHostPortGenericContainer[T] =
    new FixedHostPortGenericContainer[T]("confluentinc/cp-schema-registry:7.0.1")
      .withNetwork(network)
      .dependsOn(kafka)
      .withFixedExposedPort(8081, 8081)
      .withCreateContainerCmdModifier(_.withHostName("schema-registry"))
      .withEnv(Map(
        "SCHEMA_REGISTRY_HOST_NAME"                    -> "schema-registry",
        "SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS" -> "broker:29092",
        "SCHEMA_REGISTRY_LISTENERS"                    -> "http://0.0.0.0:8081"
      ).asJava)

}
