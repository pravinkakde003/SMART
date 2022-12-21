package com.user.smart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.user.smart.api.StoreApiServices
import com.user.smart.models.ErrorModel
import com.user.smart.models.FuelPriceAPIResponse
import com.user.smart.models.FuelPriceEditRequestBody
import com.user.smart.models.FuelPriceEditResponse
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class FuelPriceRepository @Inject constructor(private val storeApiServices: StoreApiServices) {

    private val fuelPriceAPIResponseMutableLiveData =
        MutableLiveData<NetworkResult<FuelPriceAPIResponse>>()

    val fuelPriceDataListLiveData: LiveData<NetworkResult<FuelPriceAPIResponse>>
        get() = fuelPriceAPIResponseMutableLiveData

    suspend fun getFuelPriceLiveData(storeID: String) {
        try {
            fuelPriceAPIResponseMutableLiveData.postValue(NetworkResult.Loading())
            val response = storeApiServices.getFuelPriceData(
                storeID = storeID
            )
            handleResponse(response)
        } catch (exception: Exception) {
            if (exception is SocketTimeoutException) {
                fuelPriceAPIResponseMutableLiveData.postValue(
                    NetworkResult.Error(
                        ErrorModel(
                            "SocketTimeoutException",
                            10
                        )
                    )
                )
            } else {
                fuelPriceAPIResponseMutableLiveData.postValue(
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

    private fun handleResponse(response: Response<FuelPriceAPIResponse>) {
        if (response.isSuccessful && response.body() != null) {
            fuelPriceAPIResponseMutableLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorModel = Gson().fromJson(response.errorBody()?.string(), ErrorModel::class.java)
            fuelPriceAPIResponseMutableLiveData.postValue(NetworkResult.Error(errorModel))
        } else {
            fuelPriceAPIResponseMutableLiveData.postValue(
                NetworkResult.Error(
                    ErrorModel(
                        "Exception",
                        10
                    )
                )
            )
        }
    }


    private val editFuelPriceAPIResponseMutableLiveData =
        MutableLiveData<NetworkResult<FuelPriceEditResponse>>()

    val editFuelPriceDataListLiveData: LiveData<NetworkResult<FuelPriceEditResponse>>
        get() = editFuelPriceAPIResponseMutableLiveData

    suspend fun updateFuelPriceData(requestBody: FuelPriceEditRequestBody) {
        try {
            editFuelPriceAPIResponseMutableLiveData.postValue(NetworkResult.Loading())
            val response = storeApiServices.updateFuelPrice(
                rawRequest = requestBody
            )
            handleEditFuelPriceResponse(response)
        } catch (exception: Exception) {
            if (exception is SocketTimeoutException) {
                editFuelPriceAPIResponseMutableLiveData.postValue(
                    NetworkResult.Error(
                        ErrorModel(
                            "SocketTimeoutException",
                            10
                        )
                    )
                )
            } else {
                editFuelPriceAPIResponseMutableLiveData.postValue(
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

    private fun handleEditFuelPriceResponse(response: Response<FuelPriceEditResponse>) {
        if (response.isSuccessful && response.body() != null) {
            editFuelPriceAPIResponseMutableLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorModel = Gson().fromJson(response.errorBody()?.string(), ErrorModel::class.java)
            editFuelPriceAPIResponseMutableLiveData.postValue(NetworkResult.Error(errorModel))
        } else {
            editFuelPriceAPIResponseMutableLiveData.postValue(
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