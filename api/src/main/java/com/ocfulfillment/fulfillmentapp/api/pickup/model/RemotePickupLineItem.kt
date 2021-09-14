package com.ocfulfillment.fulfillmentapp.api.pickup.model

import androidx.annotation.Keep

@Keep
data class RemotePickupLineItem(
    val articleInfo: List<RemoteArticleInfo> = emptyList<RemoteArticleInfo>(),
    val facilityRef: String = "",
    val hasSubstituteItems: Boolean = false,
    val id: String = "",
    val imageUrl: String = "",
    val orderArticleCount : Int = 0,
    val pickJobRef: String = "",
    val pickJobVersion: Int = 0,
    val pickedArticleCount : Int = 0,
    val priority: Int = 0,
    val scannableCodes: List<String> = emptyList<String>(),
    val status: String = "",
    val title: String = "",
    val tenantArticleId: String = ""
)

@Keep
data class RemoteArticleInfo(
    val info: String = "",
    val name: String = "",
    val priority: Int = 0
)

