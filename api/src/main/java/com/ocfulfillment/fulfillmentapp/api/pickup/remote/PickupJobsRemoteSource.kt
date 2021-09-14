package com.ocfulfillment.fulfillmentapp.api.pickup.remote

import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path


interface PickupJobsRemoteSource {

    @PATCH(value = "api/pickjobs/{pickJobId}")
    suspend fun patchPickJob(
        @Path("pickJobId") pickJobRefId: String,
        @Body createMyFavoritesBody: PickupJobBody
    ): Response<Unit>

    data class PickupJobBody(
        @Json(name = "version") val version: Int,
        @Json(name = "actions") val actions: List<PickupJobAction>
    )

    data class PickupJobAction(
        @Json(name = "status") val status: String,
        @Json(name = "action") val action: String
    )
}