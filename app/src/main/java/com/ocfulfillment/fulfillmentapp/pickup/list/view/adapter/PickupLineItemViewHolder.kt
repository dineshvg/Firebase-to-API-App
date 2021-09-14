package com.ocfulfillment.fulfillmentapp.pickup.list.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.ocfulfillment.fulfillmentapp.R
import com.ocfulfillment.fulfillmentapp.data.pickup.PickupLineItem
import com.ocfulfillment.fulfillmentapp.databinding.ItemLineitemPickupBinding

class PickupLineItemViewHolder(private val binding: ItemLineitemPickupBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun create(parent: ViewGroup) = PickupLineItemViewHolder(
            ItemLineitemPickupBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun bind(item: PickupLineItem, onChangeStatusRequested: (PickupLineItem) -> Unit) {
        with(binding) {
            title.text = item.title
            facilityRefValue.text = item.facilityRef
            idValue.text = item.id
            versionValue.text = item.version.toString()
            pickJobValue.text = item.pickJobRef
            articleIdValue.text = item.tenantArticleId
            statusValue.text = item.status.name.lowercase()
            imageView.load(item.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_pokemon)
                transformations(RoundedCornersTransformation(2f))
            }

            statusChangeButton.setOnClickListener {
                onChangeStatusRequested(item)
            }
        }
    }
}