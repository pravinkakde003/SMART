package com.user.smart.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.DayReconResponse
import com.user.smart.models.StoreListResponseItem
import com.user.smart.repository.DailySalesReconRepository
import com.user.smart.repository.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaySalesReconViewModel @Inject constructor(private val dailySalesReconRepository: DailySalesReconRepository) :
    ViewModel() {

    val daySalesReconLiveData: LiveData<NetworkResult<DayReconResponse>>
        get() = dailySalesReconRepository.dailySalesReconResponseLiveData


    fun callDailySalesReconAPI(
        storeID: String,
        date: String
    ) {
        viewModelScope.launch {
            dailySalesReconRepository.getDailySalesReconResponseLiveData(
                storeID = storeID,
                date = date
            )
        }
    }

    fun getStoreID(selectedStoreObject: StoreListResponseItem?): String {
        return if (null != selectedStoreObject && selectedStoreObject._id.isNotEmpty()) return selectedStoreObject._id else ""
    }
}