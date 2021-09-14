package com.ocfulfillment.fulfillmentapp.login.usecase

import com.ocfulfillment.fulfillmentapp.data.login.Email
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState.Successful
import com.ocfulfillment.fulfillmentapp.data.login.Password
import com.ocfulfillment.fulfillmentapp.data.login.repository.LoginRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class LoginUserWithEmailAndPasswordUseCaseTest {

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    private val repository : LoginRepository = mockk()

    private lateinit var useCase: LoginUserWithEmailAndPasswordUseCase

    private val email = Email("test@test.com")

    private val password = Password("secure-password")

    @Before
    fun beforeTest() {

        coEvery { repository.loginWithEmailAndPassword(email, password) } returns flowOf(Successful)
        useCase = LoginUserWithEmailAndPasswordUseCase(repository, dispatcher)
    }

    @After
    fun afterTest() {
        clearAllMocks()
        dispatcher.cancel()
        dispatcher.cancelChildren()
    }

    @Test
    fun useCaseInvoke() {

        runBlockingTest {
            val state = useCase.invoke(email, password).first()
            assertEquals(Successful, state)
        }
    }
}