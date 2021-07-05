package com.demo.kafka.springbootwithkafka.core.services

import com.demo.kafka.springbootwithkafka.controllers.dto.ProductDto
import com.demo.kafka.springbootwithkafka.core.domain.Product
import com.demo.kafka.springbootwithkafka.message.producers.ProductProducer
import com.demo.kafka.springbootwithkafka.model.ProductEntity
import com.demo.kafka.springbootwithkafka.repositories.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository,
                     private val productProducer: ProductProducer) {

    fun getById(id: Long): Product? =
        productRepository.findById(id)
            .map { ProductEntity.convert(it) }
            .orElse(null)

    fun save(product: Product): Product? {
        val productSaved = ProductEntity.convert(productRepository.save(ProductEntity.convert(product)!!))
        productProducer.sendMessage(ProductDto.convert(productSaved)!!)
        return productSaved
    }


}