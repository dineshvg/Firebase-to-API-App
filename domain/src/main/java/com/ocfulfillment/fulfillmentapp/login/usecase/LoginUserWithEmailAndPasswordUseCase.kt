package com.ocfulfillment.fulfillmentapp.login.usecase

import com.ocfulfillment.fulfillmentapp.data.login.Email
import com.ocfulfillment.fulfillmentapp.data.login.Password
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState
import com.ocfulfillment.fulfillmentapp.data.login.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LoginUserWithEmailAndPasswordUseCase(
    private val repository: LoginRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(email: Email, password: Password) : Flow<LoginResultState> =
        withContext(dispatcher) {
            repository.loginWithEmailAndPassword(email, password)
        }
}