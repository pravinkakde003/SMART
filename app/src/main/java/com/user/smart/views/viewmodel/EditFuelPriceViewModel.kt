package com.user.smart.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.FuelPriceEditRequestBody
import com.user.smart.models.FuelPriceEditResponse
import com.user.smart.repository.FuelPriceRepository
import com.user.smart.repository.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditFuelPriceViewModel @Inject constructor(private val fuelPriceRepository: FuelPriceRepository) :
    ViewModel() {

    val updateFuelPriceLiveData: LiveData<NetworkResult<FuelPriceEditResponse>>
        get() = fuelPriceRepository.editFuelPriceDataListLiveData


    fun callUpdateFuelPriceAPI(
        fuelPriceEditRequestBody: FuelPriceEditRequestBody,
    ) {
        viewModelScope.launch {
            fuelPriceRepository.updateFuelPriceData(
                requestBody = fuelPriceEditRequestBody
            )
        }
    }
}