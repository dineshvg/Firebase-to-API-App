package com.ocfulfillment.fulfillmentapp.api.login.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginRemoteSource(
    private val auth: FirebaseAuth,
    private val user: FirebaseUser
) {
    fun loginWithEmailAndPassword(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)

    fun logout() = auth.signOut()

    fun getJsonWebToken() = user.getIdToken(false)
}