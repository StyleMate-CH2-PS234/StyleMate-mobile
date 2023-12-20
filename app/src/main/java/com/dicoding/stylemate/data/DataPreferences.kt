package com.dicoding.stylemate.data

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email

internal class DataPreferences(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val EMAIL = "email"
        private const val PASS = "pass"
        private const val TOKEN = "token"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setEmailPass(email: String, pass: String){
        val editor = preferences.edit()
        editor.putString(EMAIL, email)
        editor.putString(PASS, pass)
        editor.apply()
    }

    fun getEmail() : String{
        val email = preferences.getString(EMAIL, "")
        return email!!
    }

    fun getPass(): String {
        val pass = preferences.getString(PASS, "")
        return pass!!
    }

    fun setToken(token: String){
        val editor = preferences.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String {
        val token = preferences.getString(TOKEN, "")
        return token!!
    }


}