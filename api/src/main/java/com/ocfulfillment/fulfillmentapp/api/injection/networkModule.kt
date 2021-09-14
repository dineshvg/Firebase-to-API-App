package com.ocfulfillment.fulfillmentapp.api.injection

import com.ocfulfillment.fulfillmentapp.api.login.remote.interceptor.AuthHeaderInterceptor
import com.ocfulfillment.fulfillmentapp.api.pickup.remote.PickupJobsRemoteSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL_NAME = "BASE_URL"
private const val BASE_URL = "https://api-gateway-wdlsgf2z3a-ew.a.run.app/"

val networkModule = module {

    single(named(BASE_URL_NAME)) { BASE_URL }

    single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

    single { get<Retrofit>().create(PickupJobsRemoteSource::class.java) }

    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }

    single { OkHttpClient().newBuilder()
        .addInterceptor(get<HttpLoggingInterceptor>())
        .addInterceptor(get<AuthHeaderInterceptor>())
        .build()
    }

    //json-web-token
    single { AuthHeaderInterceptor(get()) }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named(BASE_URL_NAME)))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

}