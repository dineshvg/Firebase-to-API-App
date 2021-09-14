package com.ocfulfillment.fulfillmentapp.api.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseOptions
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                @Suppress("UNCHECKED_CAST")
                if (isCanceled) cont.cancel() else cont.resume(result as T)
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}

data class FirebaseOptionsData(
    val appId: String,
    val apiKey: String,
    val projectId: String,
    val storageBucket: String
) {
    fun buildOptions() =
        FirebaseOptions.Builder()
            .setApplicationId(appId)
            .setApiKey(apiKey)
            .setProjectId(projectId)
            .setStorageBucket(storageBucket)
            .build()

}

