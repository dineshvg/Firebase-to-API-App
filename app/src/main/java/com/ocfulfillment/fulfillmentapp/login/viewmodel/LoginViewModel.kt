package com.ocfulfillment.fulfillmentapp.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ocfulfillment.fulfillmentapp.data.login.Email
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState
import com.ocfulfillment.fulfillmentapp.data.login.LoginViewState
import com.ocfulfillment.fulfillmentapp.data.login.Password
import com.ocfulfillment.fulfillmentapp.login.usecase.LoginUserWithEmailAndPasswordUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val loginUser: LoginUserWithEmailAndPasswordUseCase
): ViewModel() {

    private val mutableState = MutableLiveData<LoginResultState>()
    val state: LiveData<LoginResultState> = mutableState


    fun loginUserWithCredentials(
        emailString: String, passwordString: String
    ) {
        viewModelScope.launch {
            if(emailString.isNotEmpty() && passwordString.isNotEmpty()) {
                Pair(emailString.trim(), passwordString.trim()).letCheckNull{ trimmedEmail, trimmedPassword ->
                    loginUser(
                        email = Email(trimmedEmail),
                        password = Password(trimmedPassword)
                    ).collectLatest { resultState -> mutableState.value = resultState }
                }
            } else {
                mutableState.value = LoginViewState.Empty
            }
        }
    }
}

inline infix fun <A, B, R> Pair<A?, B?>.letCheckNull(block: (A, B) -> R): R? =
    when (null) {
        first, second -> null
        else -> block(first as A, second as B)
    }