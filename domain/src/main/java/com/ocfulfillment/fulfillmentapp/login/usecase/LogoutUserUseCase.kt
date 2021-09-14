package com.ocfulfillment.fulfillmentapp.login.usecase

import com.ocfulfillment.fulfillmentapp.data.login.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogoutUserUseCase(
    private val repository: LoginRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend operator fun invoke() = withContext(dispatcher) {
        repository.logout()
    }
}