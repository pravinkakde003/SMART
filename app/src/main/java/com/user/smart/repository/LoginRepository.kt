package com.user.smart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.user.smart.api.ApiService
import com.user.smart.models.ErrorModel
import com.user.smart.models.ForgetPasswordResponseModel
import com.user.smart.models.UserModel
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject


class LoginRepository @Inject constructor(private val apiService: ApiService) {
    private val loginResponseLiveData = MutableLiveData<NetworkResult<UserModel>>()

    val loginLiveData: LiveData<NetworkResult<UserModel>>
        get() = loginResponseLiveData


    private val forgetPasswordResponseLiveData =
        MutableLiveData<NetworkResult<ForgetPasswordResponseModel>>()

    val forgetPasswordLiveData: LiveData<NetworkResult<ForgetPasswordResponseModel>>
        get() = forgetPasswordResponseLiveData


    suspend fun login(username: String, password: String) {
        try {
            loginResponseLiveData.postValue(NetworkResult.Loading())
            val response = apiService.login(username = username, password = password)
            handleResponse(response)
        } catch (exception: Exception) {
            if (exception is SocketTimeoutException) {
                loginResponseLiveData.postValue(
                    NetworkResult.Error(
                        ErrorModel(
                            "SocketTimeoutException",
                            10
                        )
                    )
                )
            } else {
                loginResponseLiveData.postValue(
                    NetworkResult.Error(
                        ErrorModel(
                            "Network Exception",
                            10
                        )
                    )
                )
            }
        }
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


    suspend fun forgetPassword(emailID: String) {
        try {
            loginResponseLiveData.postValue(NetworkResult.Loading())
            val response = apiService.forgetPassword(emailID = emailID)
            handleForgetPasswordResponse(response)
        } catch (exception: Exception) {
            if (exception is SocketTimeoutException) {
                loginResponseLiveData.postValue(
                    NetworkResult.Error(
                        ErrorModel(
                            "SocketTimeoutException",
                            10
                        )
                    )
                )
            } else {
                loginResponseLiveData.postValue(
                    NetworkResult.Error(
                        ErrorModel(
                            "Network Exception",
                            10
                        )
                    )
                )
            }
        }
    }

    private fun handleForgetPasswordResponse(response: Response<ForgetPasswordResponseModel>) {
        if (response.isSuccessful && response.body() != null) {
            forgetPasswordResponseLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorModel = Gson().fromJson(response.errorBody()?.string(), ErrorModel::class.java)
            forgetPasswordResponseLiveData.postValue(NetworkResult.Error(errorModel))
        } else {
            forgetPasswordResponseLiveData.postValue(
                NetworkResult.Error(
                    ErrorModel(
                        "Exception",
                        10
                    )
                )
            )
        }
    }
}