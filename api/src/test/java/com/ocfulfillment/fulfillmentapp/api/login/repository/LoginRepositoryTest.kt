package com.ocfulfillment.fulfillmentapp.api.login.repository

import com.ocfulfillment.fulfillmentapp.api.login.remote.LoginRemote
import com.ocfulfillment.fulfillmentapp.data.login.Email
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState.Failure
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState.Successful
import com.ocfulfillment.fulfillmentapp.data.login.Password
import com.ocfulfillment.fulfillmentapp.data.login.repository.LoginRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class LoginRepositoryTest  {

    @MockK
    private val remote : LoginRemote = mockk()

    private lateinit var repository: LoginRepository

    private val email = Email("test@test.com")
    private val password = Password("safe-password")


    @Before
    fun beforeTest() {
        repository = LoginRepositoryImpl(remote)

       coEvery { remote.loginWithEmailAndPassword(email, password) } returns flowOf(Successful)

    }

    @Test
    fun loginWithEmailAndPasswordSuccessful() {

        runBlockingTest {
            val state = repository.loginWithEmailAndPassword(email, password).first()
            assertEquals(Successful, state)
        }
    }

    @Test
    fun loginWithEmailAndPasswordFailure() {

        coEvery { remote.loginWithEmailAndPassword(email, password) } returns flowOf(Failure)

        runBlockingTest {
            val state = repository.loginWithEmailAndPassword(email, password).first()
            assertEquals(Failure, state)

        }
    }

    }