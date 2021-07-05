package com.demo.kafka.springbootwithkafka.core.multitenant

import com.demo.kafka.springbootwithkafka.core.multitenant.ThreadLocalStorage.getTenantName
import com.demo.kafka.springbootwithkafka.core.multitenant.ThreadLocalStorage.setTenantName
import org.springframework.core.task.TaskDecorator


class TenantAwareTaskDecorator : TaskDecorator {
    override fun decorate(runnable: Runnable): Runnable {
        val tenantName = getTenantName()
        return Runnable {
            try {
                setTenantName(tenantName)
                runnable.run()
            } finally {
                setTenantName(null)
            }
        }
    }
}
