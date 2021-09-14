package com.ocfulfillment.fulfillmentapp.api.injection

import android.content.Context
import com.ocfulfillment.fulfillmentapp.api.login.local.JsonWebTokenLocal
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val PREF_AUTHENTICATION = "PREF_AUTHENTICATION"
private const val PREF_NAME = "AuthPreferences"

val localModule = module {

    factory(named(PREF_AUTHENTICATION)) {
        androidApplication().applicationContext.getSharedPreferences(PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    factory { JsonWebTokenLocal(get(named(PREF_AUTHENTICATION))) }
}