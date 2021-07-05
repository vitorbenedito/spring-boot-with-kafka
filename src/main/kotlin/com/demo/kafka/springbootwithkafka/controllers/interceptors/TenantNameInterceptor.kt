package com.demo.kafka.springbootwithkafka.controllers.interceptors

import com.demo.kafka.springbootwithkafka.core.multitenant.ThreadLocalStorage
import org.springframework.web.servlet.AsyncHandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class TenantNameInterceptor : AsyncHandlerInterceptor {
    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        // Implement your logic to extract the Tenant Name here. Another way would be to
        // parse a JWT and extract the Tenant Name from the Claims in the Token. In the
        // example code we are just extracting a Header value:
        val tenantName = request.getHeader("X-TenantID")

        // Always set the Tenant Name, so we avoid leaking Tenants between Threads even in the scenario, when no
        // Tenant is given. I do this because if somehow the afterCompletion Handler isn't called the Tenant Name
        // could still be persisted within the ThreadLocal:
        ThreadLocalStorage.setTenantName(tenantName)
        return true
    }

    @Throws(Exception::class)
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {

        // After completing the request, make sure to erase the Tenant from the current Thread. It's
        // because Spring may reuse the Thread in the Thread Pool and you don't want to leak this
        // information:
        ThreadLocalStorage.setTenantName(null)
    }
}