package com.ocfulfillment.fulfillmentapp.data.pickup.repository

import com.ocfulfillment.fulfillmentapp.data.pickup.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PickupRepository {

    suspend fun fetchPickupV1Items(facilityRef: FacilityRef): Flow<List<PickupLineItem>>

    suspend fun togglePickupLineItemStatus(item: PickupLineItem): Flow<Boolean>

    suspend fun patchPickupJobForToggle(
        pickupJobId: PickupJobId,
        version: Version,
        statusSelection: StatusSelection
    ): Response<Unit>
}