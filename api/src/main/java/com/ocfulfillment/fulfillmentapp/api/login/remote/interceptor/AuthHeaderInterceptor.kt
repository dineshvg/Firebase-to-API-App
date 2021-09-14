package com.ocfulfillment.fulfillmentapp.api.login.remote.interceptor

import com.ocfulfillment.fulfillmentapp.api.login.local.JsonWebTokenLocal
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthHeaderInterceptor(private val authLocal: JsonWebTokenLocal) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addAuthHeader(authLocal.getAuthToken())
            .build()

        return chain.proceed(request)
    }
}