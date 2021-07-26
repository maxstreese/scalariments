package com.streese.akka.debezium

import com.typesafe.scalalogging.StrictLogging
import io.debezium.embedded.Connect
import io.debezium.engine.DebeziumEngine
import io.debezium.engine.format.Avro
import io.debezium.engine.format.Json
import org.flywaydb.core.Flyway

import java.util.Properties
import scala.jdk.CollectionConverters._

object Main extends App with StrictLogging {

  Flyway
    .configure()
    .dataSource("jdbc:postgresql://localhost:5432/postgres", "admin", "password")
    .locations("migrations")
    .load()
    .migrate()

  val props = {
    val c = Map(
      "name"                              -> "debezium_test",
      "bootstrap.servers"                 -> "localhost:9092",
      "connector.class"                   -> "io.debezium.connector.postgresql.PostgresConnector",
      "plugin.name"                       -> "pgoutput",
      "offset.storage"                    -> "org.apache.kafka.connect.storage.KafkaOffsetBackingStore",
      "offset.storage.topic"              -> "debezium_test_offset",
      "offset.storage.partitions"         -> "1",
      "offset.storage.replication.factor" -> "1",
      "database.hostname"                 -> "localhost",
      "database.port"                     -> "5432",
      "database.user"                     -> "admin",
      "database.password"                 -> "password",
      "database.dbname"                   -> "postgres",
      "database.server.name"              -> "debezium_test"
    ).asJava
    val p = new Properties()
    p.putAll(c)
    p
  }

  val engine = DebeziumEngine
    .create(classOf[Connect])
    .using(props)
    .notifying(r => logger.info(s"Key: ${r.key}\tValue: ${r.value}"))
    .build()

  engine.run()

}
