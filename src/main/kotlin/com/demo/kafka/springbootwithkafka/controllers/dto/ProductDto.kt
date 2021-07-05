package com.demo.kafka.springbootwithkafka.controllers.dto

import com.demo.kafka.springbootwithkafka.core.domain.Product
import java.util.stream.Collectors
import java.util.stream.StreamSupport


data class ProductDto (
    val id: Long?,
    val name: String?,
    val description: String?
){
    companion object {
        fun convert(source: Product?): ProductDto? {
            return if (source == null) {
                null
            } else ProductDto(source.id, source.name, source.description)
        }

        fun convert(source: ProductDto?): Product? {
            return if (source == null) {
                null
            } else Product(source.id, source.name, source.description)
        }
    }

    fun convert(products: Iterable<Product?>): List<ProductDto>? {
        return StreamSupport.stream(products.spliterator(), false)
            .map<ProductDto>(ProductDto::convert)
            .collect(Collectors.toList())
    }
}