package com.user.smart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.user.smart.api.ApiService
import com.user.smart.models.UserModel

class LoginRepository(private val apiService: ApiService) {
    private val loginResponseLiveData = MutableLiveData<UserModel>()

    val loginLiveData: LiveData<UserModel>
        get() = loginResponseLiveData

    suspend fun login(username: String, password: String) {
        val result = apiService.login(username = username, password = password)
        if (result?.body() != null) {
            loginResponseLiveData.postValue(result.body())
        }
    }
}