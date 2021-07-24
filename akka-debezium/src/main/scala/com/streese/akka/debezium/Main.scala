package com.streese.akka.debezium

import com.typesafe.scalalogging.StrictLogging
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
      "name"                         -> "debezium_test",
      "connector.class"              -> "io.debezium.connector.postgresql.PostgresConnector",
      "offset.storage.file.filename" -> "/tmp/debezium_test_offsets.dat",
      "database.hostname"            -> "localhost",
      "database.port"                -> "5432",
      "database.user"                -> "admin",
      "database.password"            -> "password",
      "database.dbname"              -> "postgres",
      "database.server.name"         -> "debezium_test",
      "plugin.name"                  -> "pgoutput"
    ).asJava
    val p = new Properties()
    p.putAll(c)
    p
  }

  val engine = DebeziumEngine
    .create(classOf[Json])
    .using(props)
    .notifying(r => logger.info(s"Key: ${r.key}\tValue: ${r.value}"))
    .build()

  engine.run()

}
