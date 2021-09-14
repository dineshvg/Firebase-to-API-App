package com.ocfulfillment.fulfillmentapp.api.login.remote.interceptor

import android.annotation.SuppressLint
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.squareup.okhttp.internal.Internal.logger
import java.io.IOException
import java.lang.String

private const val AUTH_KEY = "Authorization"
private const val TOKEN_TYPE = "Bearer"

internal class LoggingInterceptor : Interceptor {
    @SuppressLint("DefaultLocale")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val t1 = System.nanoTime()
        logger.info(
            String.format(
                "Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()
            )
        )
        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime()
        logger.info(
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()
            )
        )
        return response
    }
}



internal fun okhttp3.Request.Builder.addAuthHeader(accessToken: kotlin.String?): okhttp3.Request.Builder {
    accessToken?.let {
        removeHeader(AUTH_KEY)
        addHeader(AUTH_KEY, "$TOKEN_TYPE $it")
    }

    return this
}