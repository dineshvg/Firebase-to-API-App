package com.ocfulfillment.fulfillmentapp.data.login.repository

import com.ocfulfillment.fulfillmentapp.data.login.Email
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState
import com.ocfulfillment.fulfillmentapp.data.login.Password
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun loginWithEmailAndPassword(email: Email, password: Password): Flow<LoginResultState>

    suspend fun logout()
}