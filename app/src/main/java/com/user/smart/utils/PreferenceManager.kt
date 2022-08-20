package com.user.smart.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.user.smart.models.StoreListResponseItem
import com.user.smart.utils.AppConstant.PREFERENCE_FILE
import com.user.smart.utils.AppConstant.SELECTED_SORE_KEY
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

    fun saveSelectedStoreObject(selectedStoreObject: StoreListResponseItem?) {
        val editor = preferences.edit()
        editor.putString(SELECTED_SORE_KEY, Gson().toJson(selectedStoreObject))
        editor.apply()
    }

    fun getSelectedStoreObject(): StoreListResponseItem? {
        val stringObject = preferences.getString(SELECTED_SORE_KEY, null)
        return Gson().fromJson(stringObject, StoreListResponseItem::class.java)
    }

    fun clearAllData() {
        val editor = preferences.edit()
        editor.clear().apply()
    }
}