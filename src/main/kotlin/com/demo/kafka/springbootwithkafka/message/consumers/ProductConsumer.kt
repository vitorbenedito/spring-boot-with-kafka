package com.demo.kafka.springbootwithkafka.message.consumers

import com.demo.kafka.springbootwithkafka.controllers.dto.ProductDto
import com.demo.kafka.springbootwithkafka.core.multitenant.ThreadLocalStorage
import com.demo.kafka.springbootwithkafka.core.services.ProductService
import com.demo.kafka.springbootwithkafka.message.dto.MessageDto
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
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
    fun consume(message: String?) {
        logger.info(String.format("#### -> Consumed message -> %s", message))
        val messageDto: MessageDto? = message?.let { jacksonObjectMapper().readValue(it) }
        val product: ProductDto? = messageDto?.message?.let { jacksonObjectMapper().readValue(it) }

        ThreadLocalStorage.setTenantName(messageDto?.tenant)
        val productLoaded = product?.id?.let { productService.getById(it) }
        logger.info(String.format("#### -> Product found -> %s", productLoaded))
    }

}