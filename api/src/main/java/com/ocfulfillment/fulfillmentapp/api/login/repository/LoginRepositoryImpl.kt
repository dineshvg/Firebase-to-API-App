package com.ocfulfillment.fulfillmentapp.api.login.repository

import com.ocfulfillment.fulfillmentapp.api.login.remote.LoginRemote
import com.ocfulfillment.fulfillmentapp.data.login.Email
import com.ocfulfillment.fulfillmentapp.data.login.Password
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState
import com.ocfulfillment.fulfillmentapp.data.login.repository.LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class LoginRepositoryImpl(
    private val remote: LoginRemote
): LoginRepository {

    override suspend fun loginWithEmailAndPassword(email: Email, password: Password) : Flow<LoginResultState> {
        val login = remote.loginWithEmailAndPassword(email, password)
        return login
    }


    override suspend fun logout() = remote.logout()

}

