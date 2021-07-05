package com.demo.kafka.springbootwithkafka.message.producers;

import com.demo.kafka.springbootwithkafka.controllers.dto.ProductDto
import com.demo.kafka.springbootwithkafka.core.multitenant.ThreadLocalStorage
import com.demo.kafka.springbootwithkafka.message.dto.MessageDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFutureCallback


@Service
class ProductProducer(private val kafkaTemplate:KafkaTemplate<String, String> ) {

    companion object {
        const val TOPIC = "products"
        val logger: Logger = LoggerFactory.getLogger(javaClass)
    }

    @Async
    fun sendMessage(product: ProductDto) {
        logger.info(String.format("#### -> Producing message -> %s", product));
        this.kafkaTemplate.send(
            TOPIC,
            jacksonObjectMapper().writeValueAsString(MessageDto(ThreadLocalStorage.getTenantName(), jacksonObjectMapper().writeValueAsString(product)))
        ).addCallback(object : ListenableFutureCallback<SendResult<String?, String?>?> {
            override fun onSuccess(message: SendResult<String?, String?>?){}
            override fun onFailure(throwable: Throwable){}
        })
    }
}