package com.ocfulfillment.fulfillmentapp

import android.app.Application
import com.ocfulfillment.fulfillmentapp.api.injection.firebaseModule
import com.ocfulfillment.fulfillmentapp.api.injection.localModule
import com.ocfulfillment.fulfillmentapp.api.injection.networkModule
import com.ocfulfillment.fulfillmentapp.api.injection.remoteModule
import com.ocfulfillment.fulfillmentapp.injection.domainModule
import com.ocfulfillment.fulfillmentapp.login.injection.loginModule
import com.ocfulfillment.fulfillmentapp.pickup.list.injection.pickupListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FulfillmentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@FulfillmentApplication)
            modules(
                loginModule,
                pickupListModule,
                domainModule,
                remoteModule,
                localModule,
                firebaseModule,
                networkModule
            )
        }
    }


}