package com.ocfulfillment.fulfillmentapp.login.injection

import com.ocfulfillment.fulfillmentapp.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    viewModel { LoginViewModel(get()) }
}