package com.ocfulfillment.fulfillmentapp.data.pickup

data class UserConfig(
    val userId: String,
    val role: String,
    val facilityRef: String
)

@JvmInline
value class FacilityRef(val id: String)

data class PickupLineItem(
    val id: String,
    val pickJobRef: String,
    val tenantArticleId: String,
    val status: Status,
    val facilityRef: String,
    val title: String,
    val imageUrl: String,
    val version: Int
) {
    fun toggleStatus() : Status = if(status == Status.OPEN) Status.CLOSED else Status.OPEN
}

enum class Status {
    OPEN, CLOSED
}

sealed interface PickupLineItemsViewState {
    data class Content(val items: List<PickupLineItem>, val count: Int) : PickupLineItemsViewState
    object Loading: PickupLineItemsViewState
    object Error: PickupLineItemsViewState
}

@JvmInline
value class PickupJobId(val id: String)

@JvmInline
value class Version(val versionValue: Int)

@JvmInline
value class StatusSelection(val status: Status)