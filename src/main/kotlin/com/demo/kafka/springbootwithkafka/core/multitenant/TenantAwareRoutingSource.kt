package com.demo.kafka.springbootwithkafka.core.multitenant


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

class TenantAwareRoutingSource : AbstractRoutingDataSource() {

    override fun determineCurrentLookupKey(): Any? {
        return ThreadLocalStorage.getTenantName()
    }
}