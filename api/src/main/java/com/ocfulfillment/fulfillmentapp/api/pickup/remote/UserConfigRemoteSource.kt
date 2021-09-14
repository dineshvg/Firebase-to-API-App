package com.ocfulfillment.fulfillmentapp.api.pickup.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.ocfulfillment.fulfillmentapp.api.firebase.await
import com.ocfulfillment.fulfillmentapp.api.pickup.model.RemoteUserConfig
import com.ocfulfillment.fulfillmentapp.data.pickup.FacilityRef
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class UserConfigRemoteSource(
    private val fireStore: FirebaseFirestore,
    private val uid: String
) {
    suspend fun getFacilityReference(): Flow<FacilityRef> =
        callbackFlow {
            fireStore.collection(FIREBASE_USER_CONFIG)
                .whereEqualTo(USER_ID, uid)
                .limit(1)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(RemoteUserConfig::class.java) }
                .firstOrNull()
                ?.role
                ?.facilityIds
                ?.firstOrNull()?.let(::FacilityRef)
                ?.let { facilityReferenceId ->
                 trySend(facilityReferenceId)
                }
            awaitClose()
        }

    companion object {
        private const val FIREBASE_USER_CONFIG = "mobile_android-lib_general-v1-user_configs"
        private const val USER_ID = "userId"
    }
}