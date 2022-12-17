package com.user.smart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.user.smart.api.StoreApiServices
import com.user.smart.models.ErrorModel
import com.user.smart.models.GetStoreListResponse
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class StoreListingRepository @Inject constructor(private val storeApiServices: StoreApiServices) {
    private val storeListResponseMutableLiveData =
        MutableLiveData<NetworkResult<GetStoreListResponse>>()

    val storeListLiveData: LiveData<NetworkResult<GetStoreListResponse>>
        get() = storeListResponseMutableLiveData

    suspend fun getStoreList() {
        try {
            storeListResponseMutableLiveData.postValue(NetworkResult.Loading())
            val response = storeApiServices.getStoreList()
            handleResponse(response)
            handleResponse(response)
        } catch (exception: Exception) {
            if (exception is SocketTimeoutException) {
                storeListResponseMutableLiveData.postValue(
                    NetworkResult.Error(
                        ErrorModel(
                            "SocketTimeoutException",
                            10
                        )
                    )
                )
            } else {
                storeListResponseMutableLiveData.postValue(
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

    private fun handleResponse(response: Response<GetStoreListResponse>) {
        if (response.isSuccessful && response.body() != null) {
            storeListResponseMutableLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorModel = Gson().fromJson(response.errorBody()?.string(), ErrorModel::class.java)
            storeListResponseMutableLiveData.postValue(NetworkResult.Error(errorModel))
        } else {
            storeListResponseMutableLiveData.postValue(
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