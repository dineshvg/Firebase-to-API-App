package com.ocfulfillment.fulfillmentapp.login.viewmodel

import com.ocfulfillment.fulfillmentapp.data.login.Email
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState.Successful
import com.ocfulfillment.fulfillmentapp.data.login.Password
import com.ocfulfillment.fulfillmentapp.login.usecase.LoginUserWithEmailAndPasswordUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class LoginViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    private val useCase : LoginUserWithEmailAndPasswordUseCase = mockk()

    private lateinit var viewModel: LoginViewModel

    private val email = Email("test@test.com")

    private val password = Password("secure-password")

    @Before
    fun beforeTest() {
        Dispatchers.setMain(dispatcher)
        coEvery { useCase.invoke(email, password) } returns flowOf(Successful)
        viewModel = LoginViewModel(useCase)
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
        clearAllMocks()
        dispatcher.cancel()
        dispatcher.cancelChildren()
    }

    @Test
    fun loginUserWithCredentials() {

        viewModel.loginUserWithCredentials(email.value, password.value)

        coVerify { useCase.invoke(email, password) }
    }
}