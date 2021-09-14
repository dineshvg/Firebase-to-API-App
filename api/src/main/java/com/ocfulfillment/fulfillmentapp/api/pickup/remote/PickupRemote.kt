package com.ocfulfillment.fulfillmentapp.api.pickup.remote

import com.ocfulfillment.fulfillmentapp.api.pickup.model.RemotePickupLineItem
import com.ocfulfillment.fulfillmentapp.data.pickup.FacilityRef
import com.ocfulfillment.fulfillmentapp.data.pickup.PickupLineItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow


@ExperimentalCoroutinesApi
class PickupRemote(
    private val remoteSource: PickupRemoteSource
) {

    suspend fun getPickupItems(value: FacilityRef): Flow<List<RemotePickupLineItem>> = remoteSource.getPickupItems(value)

    suspend fun togglePickupItem(item: PickupLineItem): Flow<Boolean> = remoteSource.togglePickupLineItem(item)

}