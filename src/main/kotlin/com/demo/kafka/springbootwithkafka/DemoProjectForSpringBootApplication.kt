package com.demo.kafka.springbootwithkafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude= [DataSourceAutoConfiguration::class])
class DemoProjectForSpringBootApplication

fun main(args: Array<String>) {
	runApplication<DemoProjectForSpringBootApplication>(*args)
}
