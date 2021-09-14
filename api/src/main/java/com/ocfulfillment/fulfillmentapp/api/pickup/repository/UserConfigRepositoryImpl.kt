package com.ocfulfillment.fulfillmentapp.api.pickup.repository

import com.ocfulfillment.fulfillmentapp.api.pickup.remote.UserConfigRemote
import com.ocfulfillment.fulfillmentapp.data.pickup.FacilityRef
import com.ocfulfillment.fulfillmentapp.data.pickup.repository.UserConfigRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class UserConfigRepositoryImpl (private  val remote: UserConfigRemote): UserConfigRepository {

    override suspend fun getFacilityReferenceId(): Flow<FacilityRef> =  remote.getFacilityRefForUser()
}