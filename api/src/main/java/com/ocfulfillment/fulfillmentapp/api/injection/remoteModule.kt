package com.ocfulfillment.fulfillmentapp.api.injection

import com.google.firebase.auth.FirebaseAuth
import com.ocfulfillment.fulfillmentapp.api.login.remote.LoginRemote
import com.ocfulfillment.fulfillmentapp.api.login.remote.LoginRemoteSource
import com.ocfulfillment.fulfillmentapp.api.login.repository.LoginRepositoryImpl
import com.ocfulfillment.fulfillmentapp.api.pickup.mapper.PickupLineItemMapper
import com.ocfulfillment.fulfillmentapp.api.pickup.remote.*
import com.ocfulfillment.fulfillmentapp.api.pickup.repository.PickupRepositoryImpl
import com.ocfulfillment.fulfillmentapp.api.pickup.repository.UserConfigRepositoryImpl
import com.ocfulfillment.fulfillmentapp.data.login.repository.LoginRepository
import com.ocfulfillment.fulfillmentapp.data.pickup.repository.PickupRepository
import com.ocfulfillment.fulfillmentapp.data.pickup.repository.UserConfigRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
val remoteModule = module {

    //login

    factory { LoginRemoteSource(get(), get()) }

    factory { LoginRemote(get(), get()) }

    factory<LoginRepository> { LoginRepositoryImpl(get()) }

    //user-config

    factory {
        get<FirebaseAuth>().uid?.let { uid ->
            UserConfigRemoteSource(get(), uid)
        }
    }

    factory { UserConfigRemote(get()) }

    factory<UserConfigRepository> { UserConfigRepositoryImpl(get()) }

    //pickup-job

    factory { PickupJobsRemote(get()) }

    single { get<Retrofit>().create(PickupJobsRemoteSource::class.java) }

    //pickup

    factory { PickupLineItemMapper() }

    factory { PickupRemoteSource(get()) }

    factory { PickupRemote(get()) }

    factory<PickupRepository> { PickupRepositoryImpl(get(), get(), get()) }
}