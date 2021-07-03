package com.demo.kafka.springbootwithkafka.producers;

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class Producer(private val kafkaTemplate:KafkaTemplate<String, String> ) {

    companion object {
        const val TOPIC = "users"
        val logger: Logger = LoggerFactory.getLogger(javaClass)
    }

    fun sendMessage(message: String) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }
}