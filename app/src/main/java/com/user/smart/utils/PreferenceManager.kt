package com.user.smart.utils

import android.content.Context
import android.content.SharedPreferences
import com.user.smart.utils.AppConstant.PREFERENCE_FILE
import com.user.smart.utils.AppConstant.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = preferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return preferences.getString(USER_TOKEN, null)
    }
}