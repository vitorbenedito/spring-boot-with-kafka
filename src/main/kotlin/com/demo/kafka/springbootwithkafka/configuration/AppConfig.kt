package com.demo.kafka.springbootwithkafka.configuration

import com.demo.kafka.springbootwithkafka.core.multitenant.TenantAwareRoutingSource
import com.demo.kafka.springbootwithkafka.message.dto.MessageDto
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.util.backoff.BackOff
import org.springframework.util.backoff.ExponentialBackOff
import java.io.IOException
import java.util.*
import javax.sql.DataSource


@Configuration
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "multiTenantEntityManager",
    basePackages = ["com.demo.kafka.springbootwithkafka.repositories"]
)
class AppConfig {

    @Bean(name = ["multiTenantEntityManager"])
    @Throws(IOException::class)
    fun multiTenantEntityManager(): LocalContainerEntityManagerFactoryBean? {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource()
        em.setPackagesToScan("com.demo.kafka.springbootwithkafka.model")
        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        em.setJpaProperties(additionalProperties()!!)
        return em
    }

    @Bean
    fun dataSource(): DataSource {
        val dataSource: AbstractRoutingDataSource = TenantAwareRoutingSource()
        val targetDataSources: MutableMap<Any, Any> = HashMap()
        targetDataSources["TenantOne"] = tenantOne()
        targetDataSources["TenantTwo"] = tenantTwo()
        dataSource.setTargetDataSources(targetDataSources)
        dataSource.afterPropertiesSet()
        return dataSource
    }

    @Bean
    fun converter(): RecordMessageConverter? {
        return StringJsonMessageConverter()
    }

    @Bean
    fun kafkaListenerContainerFactory(
        kafkaConsumerFactory: ConsumerFactory<Any?, Any?>?,
        template: KafkaTemplate<Any?, Any?>?
    ): ConcurrentKafkaListenerContainerFactory<*, *>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, MessageDto>()
        factory.consumerFactory = kafkaConsumerFactory!!
        factory.setRecordInterceptor(RecordInterceptorMessageDto())
        factory.setErrorHandler(
            SeekToCurrentErrorHandler(
                DeadLetterPublishingRecoverer(template!!), ExponentialBackOff(3, 1.0)
            )
        )
        return factory
    }


    fun tenantOne(): DataSource {
        val dataSource = HikariDataSource()
        dataSource.initializationFailTimeout = 0
        dataSource.maximumPoolSize = 5
        dataSource.jdbcUrl = "jdbc:mysql://127.0.0.1:3306/database1"
        dataSource.username = "dba"
        dataSource.password = "123"
        return dataSource
    }

    fun tenantTwo(): DataSource {
        val dataSource = HikariDataSource()
        dataSource.initializationFailTimeout = 0
        dataSource.maximumPoolSize = 5
        dataSource.jdbcUrl = "jdbc:mysql://127.0.0.1:3306/database2"
        dataSource.username = "dba"
        dataSource.password = "123"
        return dataSource
    }

    fun additionalProperties(): Properties? {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "none")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
        return properties
    }
}