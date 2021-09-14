package com.ocfulfillment.fulfillmentapp.pickup.list.injection

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ocfulfillment.fulfillmentapp.pickup.list.view.adapter.PickupLineItemAdapter
import com.ocfulfillment.fulfillmentapp.pickup.list.viewmodel.PickupListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pickupListModule = module {

    viewModel { PickupListViewModel(get(), get(), get(), get(), get()) }

    factory { PickupLineItemAdapter() }

    factory { LinearLayoutManager(androidApplication().applicationContext, RecyclerView.VERTICAL, false) }
}