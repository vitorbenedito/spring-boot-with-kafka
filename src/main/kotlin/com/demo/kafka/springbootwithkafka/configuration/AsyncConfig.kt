package com.demo.kafka.springbootwithkafka.configuration

import com.demo.kafka.springbootwithkafka.core.multitenant.TenantAwareTaskDecorator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurerSupport
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@Configuration
@EnableAsync
class AsyncConfig : AsyncConfigurerSupport() {

    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 7
        executor.maxPoolSize = 42
        executor.setQueueCapacity(11)
        executor.setThreadNamePrefix("TenantAwareTaskExecutor-")
        executor.setTaskDecorator(TenantAwareTaskDecorator())
        executor.initialize()
        return executor
    }
}