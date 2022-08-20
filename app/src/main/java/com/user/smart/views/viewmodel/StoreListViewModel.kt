package com.user.smart.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.GetStoreListResponse
import com.user.smart.repository.NetworkResult
import com.user.smart.repository.StoreListingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreListViewModel @Inject constructor(private val storeListingRepository: StoreListingRepository) :
    ViewModel() {

    val storeListLiveData: LiveData<NetworkResult<GetStoreListResponse>>
        get() = storeListingRepository.storeListLiveData


    fun callGetStoreListAPI() {
        viewModelScope.launch {
            storeListingRepository.getStoreList()
        }
    }
}