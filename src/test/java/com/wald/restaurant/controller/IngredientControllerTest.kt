package com.wald.restaurant.controller

import com.nhaarman.mockitokotlin2.*
import com.wald.restaurant.model.Ingredient
import com.wald.restaurant.service.IngredientService
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@WebMvcTest(IngredientController::class)
class IngredientControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var ingredientService: IngredientService

    @Test
    @WithMockUser
    fun `test - get all ingredients`() {
        val receivedIngredients = listOf(
                Ingredient().apply { name = "cName" },
                Ingredient().apply { name = "dName" },
                Ingredient().apply { name = "aName" }
        )

        whenever(ingredientService.findAll()) doReturn receivedIngredients

        mockMvc.get(INGREDIENTS_PATH) {}.andDo {
            print()
        }.andExpect {
            status { isOk }
            content { string(containsString("aName")) }
            content { string(containsString("cName")) }
            content { string(containsString("dName")) }
            view { name("ingredients") }
        }

        verify(ingredientService, only()).findAll()
    }

    companion object {
        const val INGREDIENTS_PATH = "/ingredients"
    }
}