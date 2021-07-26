package com.streese.embedded.kafka.producer

import com.streese.registravka4s.AvroSerdeConfig
import com.streese.registravka4s.GenericRecordFormat
import com.streese.registravka4s.GenericSerde
import com.streese.registravka4s.kafka.KafkaProducer
import com.typesafe.scalalogging.StrictLogging
import org.apache.kafka.clients.producer.ProducerRecord

import java.time.Instant
import scala.util.Random

object Main extends App with GenericRecordFormat with GenericSerde with StrictLogging {

  case class Instrument(isin: String, currency: String)
  case class Tick(instrument: Instrument, timestamp: Instant, price: Double)

  val config = Config()

  implicit val avroSerdeConfig: AvroSerdeConfig = AvroSerdeConfig(Seq(config.schemaRegistryUri))

  val producer = KafkaProducer[Instrument, Tick]("bootstrap.servers" -> config.kafkaBootstrapUri)

  val instrument = Instrument("DE0008469008", "PTX")
  var price = 100.0

  while (true) {
    price = Math.max(price + Random.between(-5.0, +5.0), 0.0)
    val record = new ProducerRecord(config.kafkaTopic, instrument, Tick(instrument, Instant.now(), price))
    producer.send(record)
    logger.info(s"Sent record $record to Kafka")
    Thread.sleep(1000L)
  }

}
