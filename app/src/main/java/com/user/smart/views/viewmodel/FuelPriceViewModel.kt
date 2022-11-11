package com.user.smart.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.FuelPriceAPIResponse
import com.user.smart.models.StoreListResponseItem
import com.user.smart.repository.FuelPriceRepository
import com.user.smart.repository.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FuelPriceViewModel @Inject constructor(private val fuelPriceRepository: FuelPriceRepository) :
    ViewModel() {

    val fuelPriceListLiveData: LiveData<NetworkResult<FuelPriceAPIResponse>>
        get() = fuelPriceRepository.fuelPriceDataListLiveData


    fun callGetFuelPriceLiveDataAPI(
        storeID: String,
    ) {
        viewModelScope.launch {
            fuelPriceRepository.getFuelPriceLiveData(
                storeID = storeID
            )
        }
    }

    fun getStoreID(selectedStoreObject: StoreListResponseItem?): String {
        return if (null != selectedStoreObject && selectedStoreObject._id.isNotEmpty()) return selectedStoreObject._id else ""
    }


}