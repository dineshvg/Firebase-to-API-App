package com.ocfulfillment.fulfillmentapp.pickup.usecase

import com.ocfulfillment.fulfillmentapp.data.pickup.PickupJobId
import com.ocfulfillment.fulfillmentapp.data.pickup.StatusSelection
import com.ocfulfillment.fulfillmentapp.data.pickup.Version
import com.ocfulfillment.fulfillmentapp.data.pickup.repository.PickupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdatePickupJobUseCase(
    private val repository: PickupRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        pickupJobId: PickupJobId,
        version: Version,
        statusSelection: StatusSelection
    ) = withContext(dispatcher) {
        repository.patchPickupJobForToggle(pickupJobId, version, statusSelection)
    }
}