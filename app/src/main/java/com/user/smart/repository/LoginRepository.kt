package com.user.smart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.user.smart.api.ApiService
import com.user.smart.models.ErrorModel
import com.user.smart.models.UserModel
import retrofit2.Response
import javax.inject.Inject


class LoginRepository @Inject constructor(private val apiService: ApiService) {
    private val loginResponseLiveData = MutableLiveData<NetworkResult<UserModel>>()

    val loginLiveData: LiveData<NetworkResult<UserModel>>
        get() = loginResponseLiveData

    suspend fun login(username: String, password: String) {
        loginResponseLiveData.postValue(NetworkResult.Loading())
        val response = apiService.login(username = username, password = password)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserModel>) {
        if (response.isSuccessful && response.body() != null) {
            loginResponseLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorModel = Gson().fromJson(response.errorBody()?.string(), ErrorModel::class.java)
            loginResponseLiveData.postValue(NetworkResult.Error(errorModel))
        } else {
            loginResponseLiveData.postValue(NetworkResult.Error(ErrorModel("Exception", 10)))
        }
    }
}