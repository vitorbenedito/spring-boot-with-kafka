package com.demo.kafka.springbootwithkafka.controllers

import com.demo.kafka.springbootwithkafka.controllers.dto.ProductDto
import com.demo.kafka.springbootwithkafka.core.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class CustomerController @Autowired constructor(private val service: ProductService) {


    @GetMapping("/products/{id}")
    fun get(@PathVariable("id") id: Long): ProductDto? =
        service
            .getById(id)
            .let { r -> ProductDto.convert(r) }

    @PostMapping(value = ["/products"])
    fun createProduct(@RequestBody product: ProductDto?): ResponseEntity<ProductDto>? =
        ResponseEntity.accepted().body(ProductDto.convert(ProductDto.convert(product)?.let { service.save(it) }))



}
