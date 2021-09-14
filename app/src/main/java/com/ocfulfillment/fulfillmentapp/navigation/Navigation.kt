package com.ocfulfillment.fulfillmentapp.navigation

import androidx.navigation.NavController
import com.ocfulfillment.fulfillmentapp.R

class Navigation(private val navController: NavController) {

    companion object {
        private const val PICKUP_LIST = R.id.pickupListFragment
        private const val LOGIN = R.id.loginFragment
    }


    fun toPickupList() {
        navController.navigate(PICKUP_LIST)
    }

    fun toLogin() {
        navController.navigate(LOGIN)
    }
}