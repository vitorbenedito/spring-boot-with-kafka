package com.demo.kafka.springbootwithkafka.configuration

import com.demo.kafka.springbootwithkafka.controllers.interceptors.TenantNameInterceptor
import org.springframework.context.annotation.Configuration

import org.springframework.web.servlet.config.annotation.InterceptorRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebMvcConfiguration : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(TenantNameInterceptor())
    }
}