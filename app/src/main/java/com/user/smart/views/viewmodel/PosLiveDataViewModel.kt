package com.user.smart.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.POSLiveDataResponse
import com.user.smart.models.StoreListResponseItem
import com.user.smart.repository.NetworkResult
import com.user.smart.repository.POSLiveDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PosLiveDataViewModel @Inject constructor(private val posLiveDataRepository: POSLiveDataRepository) :
    ViewModel() {

    val posLiveDataListLiveData: LiveData<NetworkResult<POSLiveDataResponse>>
        get() = posLiveDataRepository.posDataListLiveData


    fun callGetPOSLiveDataListAPI(
        storeID: String,
        date: String
    ) {
        viewModelScope.launch {
            posLiveDataRepository.getPOSLiveData(
                storeID = storeID,
                date = date
            )
        }
    }

    fun getStoreID(selectedStoreObject: StoreListResponseItem?) :String{
        return if (null != selectedStoreObject && selectedStoreObject._id.isNotEmpty()) return selectedStoreObject._id else ""
    }
}