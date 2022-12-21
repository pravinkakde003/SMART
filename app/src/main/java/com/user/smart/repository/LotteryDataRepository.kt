package com.user.smart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.user.smart.api.StoreApiServices
import com.user.smart.models.ActiveLotteryResponse
import com.user.smart.models.ErrorModel
import com.user.smart.models.POSLiveDataResponse
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class LotteryDataRepository @Inject constructor(private val storeApiServices: StoreApiServices) {

    private val activeLotteryResponseMutableLiveData =
        MutableLiveData<NetworkResult<ActiveLotteryResponse>>()

    val activeLotteryListLiveData: LiveData<NetworkResult<ActiveLotteryResponse>>
        get() = activeLotteryResponseMutableLiveData

    suspend fun getActiveLotteryListLiveDataData(storeID: String) {
        try {
            activeLotteryResponseMutableLiveData.postValue(NetworkResult.Loading())
            val response = storeApiServices.getActiveLotteryData(storeID = storeID)
            handleResponse(response)
        } catch (exception: Exception) {
            if (exception is SocketTimeoutException) {
                activeLotteryResponseMutableLiveData.postValue(
                    NetworkResult.Error(
                        ErrorModel(
                            "SocketTimeoutException",
                            10
                        )
                    )
                )
            } else {
                activeLotteryResponseMutableLiveData.postValue(
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

    private fun handleResponse(response: Response<ActiveLotteryResponse>) {
        if (response.isSuccessful && response.body() != null) {
            activeLotteryResponseMutableLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorModel = Gson().fromJson(response.errorBody()?.string(), ErrorModel::class.java)
            activeLotteryResponseMutableLiveData.postValue(NetworkResult.Error(errorModel))
        } else {
            activeLotteryResponseMutableLiveData.postValue(
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