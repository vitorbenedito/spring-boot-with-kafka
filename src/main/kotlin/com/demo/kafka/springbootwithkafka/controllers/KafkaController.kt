package com.demo.kafka.springbootwithkafka.controllers

import com.demo.kafka.springbootwithkafka.producers.Producer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/kafka")
class KafkaController(private val producer: Producer) {

    @PostMapping(value = ["/publish"])
    fun sendMessageToKafkaTopic(@RequestParam("message") message: String?) {
        producer.sendMessage(message!!)
    }

}