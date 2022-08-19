package com.user.smart.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.UserModel
import com.user.smart.repository.LoginRepository
import com.user.smart.repository.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {

    val loginLiveData: LiveData<NetworkResult<UserModel>>
        get() = loginRepository.loginLiveData


    fun callLoginAPI(username: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(username, password)
        }
    }
}