package com.demo.kafka.springbootwithkafka.message.consumers

import com.demo.kafka.springbootwithkafka.controllers.dto.ProductDto
import com.demo.kafka.springbootwithkafka.core.services.ProductService
import com.demo.kafka.springbootwithkafka.message.dto.MessageDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.io.IOException


@Service
class ProductConsumer(private val productService: ProductService) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(javaClass)
    }

    @KafkaListener(topics = ["products"], groupId = "group_id")
    @Throws(IOException::class)
    fun consume(messageDto: MessageDto?) {
        logger.info(String.format("#### -> Consumed message -> %s", messageDto))
        val product: ProductDto? = messageDto?.message?.let { jacksonObjectMapper().readValue(it) }
        val productLoaded = product?.id?.let { productService.getById(it) }
        logger.info(String.format("#### -> Product found -> %s", productLoaded))
    }

}