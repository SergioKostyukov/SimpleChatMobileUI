package com.example.simplechatmobile.util

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    var authToken: String?
        get() = prefs.getString("Token", null)
        set(value) = prefs.edit().putString("Token", value).apply()
}