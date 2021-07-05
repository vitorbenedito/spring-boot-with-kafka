package com.demo.kafka.springbootwithkafka.model

import com.demo.kafka.springbootwithkafka.core.domain.Product
import java.util.stream.Collectors
import java.util.stream.StreamSupport
import javax.persistence.*


@Entity
@Table(name = "product")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name")
    private var name: String? = null,

    @Column(name = "description")
    private var description: String? = null
) {

    companion object {
        fun convert(source: Product?): ProductEntity? {
            return if (source == null) {
                null
            } else ProductEntity(source.id, source.name, source.description)
        }

        fun convert(source: ProductEntity?): Product? {
            return if (source == null) {
                null
            } else Product(source.id, source.name, source.description)
        }
    }

    fun convert(products: Iterable<ProductEntity?>): List<Product>? {
        return StreamSupport.stream(products.spliterator(), false)
            .map<Product>(ProductEntity::convert)
            .collect(Collectors.toList())
    }
}
