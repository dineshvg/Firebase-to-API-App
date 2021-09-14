package com.ocfulfillment.fulfillmentapp.api.pickup.mapper

import com.ocfulfillment.fulfillmentapp.api.pickup.model.RemotePickupLineItem
import com.ocfulfillment.fulfillmentapp.data.pickup.PickupLineItem
import com.ocfulfillment.fulfillmentapp.data.pickup.Status

class PickupLineItemMapper {

    fun mapTo(from: RemotePickupLineItem) : PickupLineItem =
            PickupLineItem(
                id = from.id,
                pickJobRef = from.pickJobRef,
                tenantArticleId = from.tenantArticleId,
                status = Status.valueOf(from.status),
                facilityRef = from.facilityRef,
                title = from.title.trim(),
                imageUrl = from.imageUrl,
                version = from.pickJobVersion
            )

}