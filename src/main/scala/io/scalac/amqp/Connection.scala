package io.scalac.amqp

import com.typesafe.config.{ConfigFactory, Config}

import io.scalac.amqp.impl.RabbitConnection

import org.reactivestreams.{Subscriber, Publisher}


object Connection {
  def apply(): Connection =
    apply(ConfigFactory.load())

  def apply(config: Config): Connection =
    apply(ConnectionSettings(config))

  def apply(settings: ConnectionSettings): Connection =
    new RabbitConnection(settings)
}

trait Connection {
  def declare(exchange: Exchange): Unit
  def declare(queue: Queue): Unit

  def deleteQueue(name: String): Unit
  def deleteExchange(name: String): Unit

  def consume(queue: String): Publisher[Delivery]

  def publish(exchange: String, routingKey: String): Subscriber[Message]

  def publishDirectly(queue: String): Subscriber[Message]
}
