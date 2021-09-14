package com.ocfulfillment.fulfillmentapp.injection

import com.ocfulfillment.fulfillmentapp.login.usecase.LoginUserWithEmailAndPasswordUseCase
import com.ocfulfillment.fulfillmentapp.login.usecase.LogoutUserUseCase
import com.ocfulfillment.fulfillmentapp.pickup.usecase.GetFacilityRefIdUseCase
import com.ocfulfillment.fulfillmentapp.pickup.usecase.GetPickupV1ItemsUseCase
import com.ocfulfillment.fulfillmentapp.pickup.usecase.TogglePickupV1ItemUseCase
import com.ocfulfillment.fulfillmentapp.pickup.usecase.UpdatePickupJobUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { LoginUserWithEmailAndPasswordUseCase(get()) }

    factory { LogoutUserUseCase(get()) }

    factory { GetFacilityRefIdUseCase(get()) }

    factory { GetPickupV1ItemsUseCase(get()) }

    factory { TogglePickupV1ItemUseCase(get()) }

    factory { UpdatePickupJobUseCase(get()) }
}