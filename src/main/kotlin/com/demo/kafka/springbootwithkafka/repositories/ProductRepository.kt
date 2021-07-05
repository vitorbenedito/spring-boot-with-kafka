package com.demo.kafka.springbootwithkafka.repositories

import com.demo.kafka.springbootwithkafka.model.ProductEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<ProductEntity?, Long?>