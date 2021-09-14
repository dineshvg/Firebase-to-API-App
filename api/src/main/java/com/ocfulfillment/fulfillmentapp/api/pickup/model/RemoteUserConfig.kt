package com.ocfulfillment.fulfillmentapp.api.pickup.model

import androidx.annotation.Keep

@Keep
data class RemoteUserConfig(
    val userId: String = "",
    val version: Int = 0,
    val role: Role = Role()
)

@Keep
data class Role(
    val name: String = "",
    val facilityIds: List<String> = emptyList()
)
