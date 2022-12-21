package com.user.smart.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.ActiveLotteryResponse
import com.user.smart.models.StoreListResponseItem
import com.user.smart.repository.LotteryDataRepository
import com.user.smart.repository.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveLotteryViewModel @Inject constructor(private val lotteryRepository: LotteryDataRepository) :
    ViewModel() {

    val activeLotteryListLiveData: LiveData<NetworkResult<ActiveLotteryResponse>>
        get() = lotteryRepository.activeLotteryListLiveData


    fun callGetActiveLotteryListAPI(storeID: String) {
        viewModelScope.launch {
            lotteryRepository.getActiveLotteryListLiveDataData(storeID = storeID)
        }
    }

    fun getStoreID(selectedStoreObject: StoreListResponseItem?): String {
        return if (null != selectedStoreObject && selectedStoreObject._id.isNotEmpty()) return selectedStoreObject._id else ""
    }
}