package com.wald.restaurant.controller

import com.wald.restaurant.model.Dish
import com.wald.restaurant.model.Manager
import com.wald.restaurant.util.convertToString
import com.wald.restaurant.util.findAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import kotlin.test.assertNotNull


@SpringBootTest
class ManagerControllerTest {}