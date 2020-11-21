package com.wald.restaurant.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import javax.persistence.EntityManager


/**
 * This [EntityManager] extension provides an ability to fetch
 * all records of the [T] type from its persistence context
 */
inline fun <reified T> EntityManager.findAll(): List<T> {
    val criteriaQuery = this.criteriaBuilder.createQuery(T::class.java)
    val typedQuery = this.createQuery(criteriaQuery.select(criteriaQuery.from(T::class.java)))

    return typedQuery.resultList
}

/**
 * Returns JSON representation of this object or result of [Object.toString] invocation
 * if any exception occurres during object conversion.
 *
 * @see OBJECT_MAPPER
 */
fun <T> T.convertToString(): String {
    try {
        return OBJECT_MAPPER.writeValueAsString(this)
    } catch (e: JsonProcessingException) {
        throw e
        return this.toString()
    }
}

/**
 * An [ObjectMapper] instance which supports the `pretty print` feature and
 * handles Hibernate's lazy-loaded fields.
 */
val OBJECT_MAPPER = ObjectMapper().apply {
    configure(SerializationFeature.INDENT_OUTPUT, true)
    registerModule(Hibernate5Module())
}