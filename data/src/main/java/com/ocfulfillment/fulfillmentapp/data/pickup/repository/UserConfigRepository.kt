package com.ocfulfillment.fulfillmentapp.data.pickup.repository

import com.ocfulfillment.fulfillmentapp.data.pickup.FacilityRef
import kotlinx.coroutines.flow.Flow

interface UserConfigRepository {

    suspend fun getFacilityReferenceId() : Flow<FacilityRef>
}