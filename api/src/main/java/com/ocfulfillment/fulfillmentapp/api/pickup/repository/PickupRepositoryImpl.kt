package com.ocfulfillment.fulfillmentapp.api.pickup.repository

import com.ocfulfillment.fulfillmentapp.api.pickup.mapper.PickupLineItemMapper
import com.ocfulfillment.fulfillmentapp.api.pickup.remote.PickupJobsRemote
import com.ocfulfillment.fulfillmentapp.api.pickup.remote.PickupRemote
import com.ocfulfillment.fulfillmentapp.data.pickup.*
import com.ocfulfillment.fulfillmentapp.data.pickup.repository.PickupRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

@ExperimentalCoroutinesApi
class PickupRepositoryImpl(
    private val remote: PickupRemote,
    private val pickupJobsRemote: PickupJobsRemote,
    private val mapper: PickupLineItemMapper
) : PickupRepository {

    override suspend fun fetchPickupV1Items(facilityRef: FacilityRef): Flow<List<PickupLineItem>> =
        remote.getPickupItems(facilityRef)
            .map { remoteList -> remoteList.map { mapper.mapTo(it) } }

    override suspend fun togglePickupLineItemStatus(item: PickupLineItem): Flow<Boolean> =
        remote.togglePickupItem(item)

    override suspend fun patchPickupJobForToggle(
        pickupJobId: PickupJobId,
        version: Version,
        statusSelection: StatusSelection
    ): Response<Unit> = pickupJobsRemote.patchPickupJobOnToggle(pickupJobId, version, statusSelection)


}

