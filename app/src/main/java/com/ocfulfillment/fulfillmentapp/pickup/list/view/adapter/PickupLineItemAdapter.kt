package com.ocfulfillment.fulfillmentapp.pickup.list.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ocfulfillment.fulfillmentapp.data.pickup.PickupLineItem

class PickupLineItemAdapter: ListAdapter<PickupLineItem, PickupLineItemViewHolder>(DiffCallback) {

    object DiffCallback: DiffUtil.ItemCallback<PickupLineItem>() {

        override fun areItemsTheSame(oldItem: PickupLineItem, newItem: PickupLineItem): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: PickupLineItem, newItem: PickupLineItem): Boolean = oldItem.status == newItem.status

    }

    lateinit var onChangeStatusRequested: (PickupLineItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PickupLineItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: PickupLineItemViewHolder, position: Int) {
        holder.bind(getItem(position), onChangeStatusRequested)
    }
}