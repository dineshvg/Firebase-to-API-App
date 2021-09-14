package com.ocfulfillment.fulfillmentapp.api.login.remote

import com.ocfulfillment.fulfillmentapp.api.login.local.JsonWebTokenLocal
import com.ocfulfillment.fulfillmentapp.data.login.Email
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState
import com.ocfulfillment.fulfillmentapp.data.login.Password
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.channelFlow
import timber.log.Timber

@ExperimentalCoroutinesApi
class LoginRemote (
    private val remoteSource: LoginRemoteSource,
    private val local: JsonWebTokenLocal
) : Flow<LoginResultState> {

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
                                saveAccessToken()
                                sendSuccess()
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

    /**
     * Save the JWT to local
     */
    private fun saveAccessToken() = remoteSource.getJsonWebToken().addOnCompleteListener { task ->
        channelFlow {
            if(task.exception != null ){
                Timber.e(task.exception)
                trySend(false)
            }

            when {
                task.isSuccessful -> {
                    val token = task.result?.token
                    token?.let {
                        local.saveAuthToken(it)
                        trySend(true)
                    } ?: trySend(false)
                }

                task.isCanceled -> trySend(false)
            }

            awaitClose()
        }
    }

    private fun ProducerScope<LoginResultState>.sendFailure() =
        trySend(LoginResultState.Failure)

    private fun ProducerScope<LoginResultState>.sendSuccess() =
        trySend(LoginResultState.Successful)


    private fun ProducerScope<LoginResultState>.sendCancelled() =
        trySend(LoginResultState.Cancelled)

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<LoginResultState>) {
        //don't collect here
    }

}