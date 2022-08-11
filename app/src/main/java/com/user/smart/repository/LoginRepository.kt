package com.user.smart.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.user.smart.api.ApiService
import com.user.smart.models.UserModel

class LoginRepository(private val apiService: ApiService) {
    private val loginResponseLiveData = MutableLiveData<NetworkResult<UserModel>>()

    val loginLiveData: LiveData<NetworkResult<UserModel>>
        get() = loginResponseLiveData

    suspend fun login(username: String, password: String) {
        val result = apiService.login(username = username, password = password)
        if (result?.body() != null) {
            Log.e("Response : ", result.body().toString())
            loginResponseLiveData.postValue(NetworkResult.Success(result.body()))
        } else {
            loginResponseLiveData.postValue(NetworkResult.Error("Error"))
        }
    }
}