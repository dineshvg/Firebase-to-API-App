package com.ocfulfillment.fulfillmentapp.data.login

@JvmInline
value class Email(val value: String)

@JvmInline
value class Password(val value: String)

sealed interface LoginResultState {
    object Successful: LoginResultState
    object Failure: LoginResultState
    object Cancelled: LoginResultState
}

sealed interface LoginViewState: LoginResultState {
    object Empty: LoginViewState
}