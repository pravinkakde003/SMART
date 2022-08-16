package com.user.smart.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.user.smart.api.ApiService
import com.user.smart.models.ErrorModel
import com.user.smart.models.UserModel


class LoginRepository(private val apiService: ApiService) {
    private val loginResponseLiveData = MutableLiveData<NetworkResult<UserModel>>()

    val loginLiveData: LiveData<NetworkResult<UserModel>>
        get() = loginResponseLiveData

    suspend fun login(username: String, password: String) {
        loginResponseLiveData.postValue(NetworkResult.Loading())
        val response = apiService.login(username = username, password = password)
        Log.e("Response : ", response.body().toString())
        if (response.isSuccessful && response.body() != null) {
            loginResponseLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorModel = Gson().fromJson(response.errorBody()?.string(), ErrorModel::class.java)
            loginResponseLiveData.postValue(NetworkResult.Error(errorModel))
        } else {
            loginResponseLiveData.postValue(NetworkResult.Error(ErrorModel("ssss", 10)))
        }
    }
}