package com.demo.kafka.springbootwithkafka.configuration

import com.demo.kafka.springbootwithkafka.core.multitenant.ThreadLocalStorage
import com.demo.kafka.springbootwithkafka.message.dto.MessageDto
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.listener.RecordInterceptor

class RecordInterceptorMessageDto : RecordInterceptor<String, MessageDto> {
    override fun intercept(record: ConsumerRecord<String, MessageDto>): ConsumerRecord<String, MessageDto>? {
        ThreadLocalStorage.setTenantName(record.value()?.tenant)
        return record
    }

}