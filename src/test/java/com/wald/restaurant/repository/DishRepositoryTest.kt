package com.wald.restaurant.repository

import com.wald.restaurant.model.Dish
import com.wald.restaurant.util.findWhere
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import kotlin.test.assertEquals

@SpringBootTest
class DishRepositoryTest {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var dishRepository: DishRepository

    @Test
    fun `test - find all`() {
        val expectedEntities = entityManager.findWhere<Dish> { _, _ -> null }
        val actualRecords = dishRepository.findAll()

        assertArrayEquals(expectedEntities.toTypedArray(), actualRecords.toTypedArray())
    }

    @Test
    fun `test findByName`() {
        val expectedEntities = entityManager.findWhere<Dish> { builder, root ->
            builder.equal(root.get<String>("name"), "Сырники")
        }
        val actualRecords = dishRepository.findByName("Сырники")

        assertEquals(expectedEntities.first(), actualRecords.orElse(null))
    }
}