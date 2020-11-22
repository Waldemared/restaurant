package com.wald.restaurant.controller

import com.nhaarman.mockitokotlin2.*
import com.wald.restaurant.service.ManagerService
import com.wald.restaurant.util.SUCCESS_ONLY_SET
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.*


@WebMvcTest(ManagerController::class)
class ManagerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var managerService: ManagerService

    @Test
    @WithMockUser
    fun `test - register new user`() {
        val expectedName = "testName"
        val expectedLogin = "testLogin"
        val expectedPassword = "testPassword"

        whenever(managerService.add(expectedName, expectedLogin, expectedPassword)) doReturn SUCCESS_ONLY_SET

        mockMvc.post(REGISTER_PATH) {
            param("name", expectedName)
            param("login", expectedLogin)
            param("password", expectedPassword)
        }.andDo {
            print()
        }.andExpect {
            status { is3xxRedirection }
            redirectedUrl(REGISTER_PATH)
            flash { attribute("info", SUCCESS_ONLY_SET) }
        }

        verify(managerService, only()).add(any(), any(), any())
    }

    companion object {
        const val REGISTER_PATH = "/register"
    }
}