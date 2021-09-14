package com.ocfulfillment.fulfillmentapp.api.pickup.remote

import com.ocfulfillment.fulfillmentapp.data.pickup.FacilityRef
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class UserConfigRemote(private val remoteSource: UserConfigRemoteSource) {

    suspend fun getFacilityRefForUser(): Flow<FacilityRef> = remoteSource.getFacilityReference()
}