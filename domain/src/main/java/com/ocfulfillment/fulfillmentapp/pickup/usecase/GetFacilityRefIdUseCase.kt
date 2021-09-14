package com.ocfulfillment.fulfillmentapp.pickup.usecase

import com.ocfulfillment.fulfillmentapp.data.pickup.FacilityRef
import com.ocfulfillment.fulfillmentapp.data.pickup.repository.UserConfigRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetFacilityRefIdUseCase(
    private val repository: UserConfigRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): Flow<FacilityRef> =
        withContext(dispatcher) {
            repository.getFacilityReferenceId()
        }
}