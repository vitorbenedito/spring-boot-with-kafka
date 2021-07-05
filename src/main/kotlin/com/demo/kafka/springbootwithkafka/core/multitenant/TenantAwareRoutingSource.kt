package com.demo.kafka.springbootwithkafka.core.multitenant

import com.demo.kafka.springbootwithkafka.core.multitenant.ThreadLocalStorage.getTenantName
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

class TenantAwareRoutingSource : AbstractRoutingDataSource() {

    override fun determineCurrentLookupKey(): Any? {
        return getTenantName()
    }
}