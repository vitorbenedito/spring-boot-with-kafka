package com.demo.kafka.springbootwithkafka.consumers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import java.io.IOException


class Consumer {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(javaClass)
    }

    @KafkaListener(topics = ["user"], groupId = "group_id")
    @Throws(IOException::class)
    fun consume(message: String?) {
        logger.info(String.format("#### -> Consumed message -> %s", message))
    }

}