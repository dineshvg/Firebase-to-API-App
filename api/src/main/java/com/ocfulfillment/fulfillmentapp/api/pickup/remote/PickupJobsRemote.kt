package com.ocfulfillment.fulfillmentapp.api.pickup.remote

import com.ocfulfillment.fulfillmentapp.api.pickup.remote.PickupJobsRemoteSource.PickupJobAction
import com.ocfulfillment.fulfillmentapp.data.pickup.PickupJobId
import com.ocfulfillment.fulfillmentapp.data.pickup.StatusSelection
import com.ocfulfillment.fulfillmentapp.data.pickup.Version

class PickupJobsRemote(private val pickupJobsRemoteSource: PickupJobsRemoteSource) {

    suspend fun patchPickupJobOnToggle(
        pickupJobId: PickupJobId,
        version: Version,
        statusSelection: StatusSelection
    ) =
        pickupJobsRemoteSource.patchPickJob(
            pickJobRefId = pickupJobId.id,
            PickupJobsRemoteSource.PickupJobBody(
                version = version.versionValue,
                actions = listOf(
                    PickupJobAction(
                        status = statusSelection.status.name,
                        action = "ModifyPickJob"
                    )
                )
            )
        )
}