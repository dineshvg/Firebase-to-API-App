package com.ocfulfillment.fulfillmentapp.pickup.usecase

import com.ocfulfillment.fulfillmentapp.data.pickup.FacilityRef
import com.ocfulfillment.fulfillmentapp.data.pickup.PickupLineItem
import com.ocfulfillment.fulfillmentapp.data.pickup.repository.PickupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetPickupV1ItemsUseCase(
    private val pickupRepository: PickupRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend operator fun invoke(facilityRefId: FacilityRef) : Flow<List<PickupLineItem>> =
        withContext(dispatcher) {
            pickupRepository.fetchPickupV1Items(facilityRefId)
        }
}