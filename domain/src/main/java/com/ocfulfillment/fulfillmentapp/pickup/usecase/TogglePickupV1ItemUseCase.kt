package com.ocfulfillment.fulfillmentapp.pickup.usecase

import com.ocfulfillment.fulfillmentapp.data.pickup.PickupLineItem
import com.ocfulfillment.fulfillmentapp.data.pickup.repository.PickupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TogglePickupV1ItemUseCase(
    private val repository: PickupRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(item: PickupLineItem): Flow<Boolean> =
        withContext(dispatcher) {
            val modifiedItem = item.copy(status = item.toggleStatus())
            repository.togglePickupLineItemStatus(modifiedItem)
        }

}