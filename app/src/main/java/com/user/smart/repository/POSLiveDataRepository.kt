package com.user.smart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.user.smart.api.StoreApiServices
import com.user.smart.models.ErrorModel
import com.user.smart.models.POSLiveDataResponse
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class POSLiveDataRepository @Inject constructor(private val storeApiServices: StoreApiServices) {
    private val posLiveDataResponseMutableLiveData =
        MutableLiveData<NetworkResult<POSLiveDataResponse>>()

    val posDataListLiveData: LiveData<NetworkResult<POSLiveDataResponse>>
        get() = posLiveDataResponseMutableLiveData

    suspend fun getPOSLiveData(
        storeID: String,
        date: String
    ) {
        try {
            posLiveDataResponseMutableLiveData.postValue(NetworkResult.Loading())
            val response = storeApiServices.getPOSLiveData(
                storeID = storeID,
                date = date
            )
            handleResponse(response)
        } catch (exception: Exception) {
            if (exception is SocketTimeoutException) {
                posLiveDataResponseMutableLiveData.postValue(
                    NetworkResult.Error(
                        ErrorModel(
                            "SocketTimeoutException",
                            10
                        )
                    )
                )
            } else {
                posLiveDataResponseMutableLiveData.postValue(
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

    private fun handleResponse(response: Response<POSLiveDataResponse>) {
        if (response.isSuccessful && response.body() != null) {
            posLiveDataResponseMutableLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorModel = Gson().fromJson(response.errorBody()?.string(), ErrorModel::class.java)
            posLiveDataResponseMutableLiveData.postValue(NetworkResult.Error(errorModel))
        } else {
            posLiveDataResponseMutableLiveData.postValue(
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