package com.demo.kafka.springbootwithkafka.core.multitenant

object ThreadLocalStorage {

    var tenant = ThreadLocal<String>()

    fun setTenantName(tenantName: String?) {
        tenant.set(tenantName)
    }

    fun getTenantName(): String? {
        return tenant.get()
    }
}