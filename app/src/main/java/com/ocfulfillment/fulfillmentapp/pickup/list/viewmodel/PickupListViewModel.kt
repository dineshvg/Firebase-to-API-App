package com.ocfulfillment.fulfillmentapp.pickup.list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ocfulfillment.fulfillmentapp.data.pickup.*
import com.ocfulfillment.fulfillmentapp.login.usecase.LogoutUserUseCase
import com.ocfulfillment.fulfillmentapp.pickup.usecase.GetFacilityRefIdUseCase
import com.ocfulfillment.fulfillmentapp.pickup.usecase.GetPickupV1ItemsUseCase
import com.ocfulfillment.fulfillmentapp.pickup.usecase.TogglePickupV1ItemUseCase
import com.ocfulfillment.fulfillmentapp.pickup.usecase.UpdatePickupJobUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PickupListViewModel(
    private val getFacilityRefId: GetFacilityRefIdUseCase,
    private val getPickupItems: GetPickupV1ItemsUseCase,
    private val togglePickupItem: TogglePickupV1ItemUseCase,
    private val updatePickupJob: UpdatePickupJobUseCase,
    private val logout: LogoutUserUseCase
) : ViewModel() {

    private val mutableState = MutableLiveData<PickupLineItemsViewState>()
    val state: LiveData<PickupLineItemsViewState> = mutableState

    fun initialize() {
        setLoading()
        load()
    }

    private fun load() {
        viewModelScope.launch {
            getFacilityRefId()
                .collectLatest { facilityRefId ->
                    getPickupItems(facilityRefId)
                        .collectLatest { lineItems ->
                            Log.d("line-items", "$lineItems")
                            mutableState.value =
                                PickupLineItemsViewState.Content(lineItems, lineItems.size)
                        }
                }
        }
    }

    private fun patchDetailsForSelectedPickupJob(item: PickupLineItem) {
        viewModelScope.launch {
           val message =  updatePickupJob(
                pickupJobId = PickupJobId(item.pickJobRef),
                version = Version(item.version),
                statusSelection = StatusSelection(item.toggleStatus())
            ).message()
            Log.d("pickup-job-status-msg", message)
        }
    }

    fun toggleStatus(item: PickupLineItem) {
        viewModelScope.launch {
            togglePickupItem(item)
                .collectLatest { isSuccessful ->
                    if (isSuccessful) {
                        load()
                        patchDetailsForSelectedPickupJob(item)
                    } else {
                        mutableState.value = PickupLineItemsViewState.Error
                    }
                }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            logout()
        }
    }

    private fun setLoading() {
        mutableState.value = PickupLineItemsViewState.Loading
    }
}