package com.ocfulfillment.fulfillmentapp.api.pickup.remote

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore
import com.ocfulfillment.fulfillmentapp.api.firebase.await
import com.ocfulfillment.fulfillmentapp.api.pickup.model.RemotePickupLineItem
import com.ocfulfillment.fulfillmentapp.data.pickup.FacilityRef
import com.ocfulfillment.fulfillmentapp.data.pickup.PickupLineItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class PickupRemoteSource(
    private val fireStore: FirebaseFirestore
) {

    suspend fun getPickupItems(facilityRef: FacilityRef): Flow<List<RemotePickupLineItem>> =
        callbackFlow {
            val items = fireStore.collection(FIREBASE_PICKUP_V1)
                .whereEqualTo(FACILITY_REF, facilityRef.id)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(RemotePickupLineItem::class.java) }
            trySend(items)
            awaitClose()
        }

    @SuppressLint("LogNotTimber")
    suspend fun togglePickupLineItem(item: PickupLineItem): Flow<Boolean> =
        callbackFlow {
            fireStore.collection(FIREBASE_PICKUP_V1)
                .document(item.id)
                .update(STATUS, item.status.name)
                .addOnSuccessListener { trySend(true) }
                .addOnCanceledListener { trySend(false) }
                .addOnFailureListener { trySend(false) }
            awaitClose()
        }

    companion object {

        private const val FIREBASE_PICKUP_V1 = "mobile_android-picking-v1-picklineitems"
        private const val FACILITY_REF = "facilityRef"
        private const val STATUS = "status"
    }
}