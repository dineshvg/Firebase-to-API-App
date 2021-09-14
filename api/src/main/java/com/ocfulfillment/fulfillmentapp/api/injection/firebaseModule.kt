package com.ocfulfillment.fulfillmentapp.api.injection

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ocfulfillment.fulfillmentapp.api.firebase.FirebaseOptionsData
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val FB_APP_ID = "1:456823708012:android:8fe4a96a7ecd9b0508fb49"
private const val FB_API_KEY = "AIzaSyDQEK-D_N2E8KBh9ouGCT5i9GRjNYK4-vo"
private const val FB_PROJECT_ID = "ocff-yellowbear-git"
private const val FB_STORAGE_BUCKET = "ocff-yellowbear-git.appspot.com"

val firebaseModule = module {

    single { FirebaseApp.initializeApp(androidContext(), get<FirebaseOptionsData>().buildOptions()) }

    single { FirebaseAuth.getInstance(get()) }

    single { FirebaseFirestore.getInstance() }

    single { FirebaseAuth.getInstance().currentUser }

    factory {
        FirebaseOptionsData(
            appId = FB_APP_ID,
            apiKey = FB_API_KEY,
            projectId = FB_PROJECT_ID,
            storageBucket = FB_STORAGE_BUCKET
        )
    }
}