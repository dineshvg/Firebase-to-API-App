package com.ocfulfillment.fulfillmentapp.api.login.remote

import com.ocfulfillment.fulfillmentapp.api.login.local.JsonWebTokenLocal
import com.ocfulfillment.fulfillmentapp.data.login.Email
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState
import com.ocfulfillment.fulfillmentapp.data.login.Password
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

@ExperimentalCoroutinesApi
class LoginRemote (
    private val remoteSource: LoginRemoteSource,
    private val local: JsonWebTokenLocal
) {

    suspend fun loginWithEmailAndPassword(
        email: Email,
        password: Password
    ) : Flow<LoginResultState> =
        channelFlow {
            remoteSource.loginWithEmailAndPassword(
                email.value, password.value
            )
                .addOnCompleteListener { task ->
                    if (task.exception != null) {
                        sendFailure()
                    }
                    when {
                        task.isSuccessful -> {
                            task.result?.user?.uid?.let { _ ->
                                remoteSource.listenToJsonWebToken().addOnCompleteListener { tokenTask ->
                                    val token = tokenTask.result?.token
                                    token?.let {
                                        local.saveAuthToken(it)
                                        sendSuccess()
                                    } ?: sendFailure()
                                }
                            } ?: sendFailure()
                        }
                        task.isCanceled -> sendCancelled()
                    }
                }
            awaitClose()
        }

    /**
     * Delete the JWT on logout
     */
    fun logout()  {
        remoteSource.logout()
        local.deleteAuthToken()
    }

    private fun ProducerScope<LoginResultState>.sendFailure() =
        trySend(LoginResultState.Failure)

    private fun ProducerScope<LoginResultState>.sendSuccess() =
        trySend(LoginResultState.Successful)


    private fun ProducerScope<LoginResultState>.sendCancelled() =
        trySend(LoginResultState.Cancelled)

}