package com.user.smart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.user.smart.api.StoreApiServices
import com.user.smart.models.DayReconResponse
import com.user.smart.models.ErrorModel
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class DailySalesReconRepository @Inject constructor(private val storeApiServices: StoreApiServices) {

    private val dailySalesReconResponseMutableLiveData =
        MutableLiveData<NetworkResult<DayReconResponse>>()

    val dailySalesReconResponseLiveData: LiveData<NetworkResult<DayReconResponse>>
        get() = dailySalesReconResponseMutableLiveData

    suspend fun getDailySalesReconResponseLiveData(
        storeID: String,
        date: String
    ) {
        try {
            dailySalesReconResponseMutableLiveData.postValue(NetworkResult.Loading())
            val response = storeApiServices.getDaySalesReconLiveData(
                storeID = storeID,
                date = date
            )
            handleResponse(response)
        } catch (exception: Exception) {
            if (exception is SocketTimeoutException) {
                dailySalesReconResponseMutableLiveData.postValue(
                    NetworkResult.Error(
                        ErrorModel(
                            "SocketTimeoutException",
                            10
                        )
                    )
                )
            } else {
                dailySalesReconResponseMutableLiveData.postValue(
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

    private fun handleResponse(response: Response<DayReconResponse>) {
        if (response.isSuccessful && response.body() != null) {
            dailySalesReconResponseMutableLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorModel = Gson().fromJson(response.errorBody()?.string(), ErrorModel::class.java)
            dailySalesReconResponseMutableLiveData.postValue(NetworkResult.Error(errorModel))
        } else {
            dailySalesReconResponseMutableLiveData.postValue(
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