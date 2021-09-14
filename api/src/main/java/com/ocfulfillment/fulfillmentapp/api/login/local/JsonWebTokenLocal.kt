package com.ocfulfillment.fulfillmentapp.api.login.local

import android.content.SharedPreferences
import androidx.core.content.edit

class JsonWebTokenLocal(private val sharedPreferences: SharedPreferences) {

    fun saveAuthToken(jsonWebToken: String) {
        sharedPreferences.edit(commit = true) {
            putString(PREFS_JSON_WEB_TOKEN, jsonWebToken)
        }
    }

    fun getAuthToken(): String? = sharedPreferences.getString(PREFS_JSON_WEB_TOKEN, null)

    fun deleteAuthToken() {
        sharedPreferences.edit(commit = true) {
            clear()
        }
    }

    private companion object {

        const val PREFS_JSON_WEB_TOKEN = "PREFS_JSON_WEB_TOKEN"

    }
}