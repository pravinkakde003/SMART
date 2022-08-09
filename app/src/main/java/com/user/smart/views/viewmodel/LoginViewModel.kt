package com.user.smart.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.UserModel
import com.user.smart.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    val loginLiveData: LiveData<UserModel>
        get() = loginRepository.loginLiveData


    fun callLoginAPI(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.login(username, password)
        }
    }
}