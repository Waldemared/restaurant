package com.wald.restaurant.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root


/**
 * This [EntityManager] extension provides an ability to fetch entities of the [T] type from its persistence context.
 * Filters entities that do not satisfy the conditions provided by [predicateCreator], if the one is present.
 */
inline fun <reified T> EntityManager.findWhere(predicateCreator: (CriteriaBuilder, Root<T>) -> Predicate?): List<T> {
    val criteriaQuery = this.criteriaBuilder.createQuery(T::class.java)
    val root = criteriaQuery.from(T::class.java)

    criteriaQuery.select(root)
    predicateCreator(this.criteriaBuilder, root)?.also {
        criteriaQuery.where(it)
    }

    val typedQuery = this.createQuery(criteriaQuery)

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

/**
 * A flash attribute that should be returned by controller by `info` key on successful invocation
 */
val SUCCESS_ONLY_SET = setOf("success")