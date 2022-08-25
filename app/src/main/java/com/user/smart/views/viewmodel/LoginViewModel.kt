package com.user.smart.views.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.UserModel
import com.user.smart.repository.LoginRepository
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.AppUtils
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

    fun validateCredentials(emailAddress: String,password: String) : Pair<Boolean, String> {
        var result = Pair(true, "")
        if(TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide the credentials")
        }
        else if(!AppUtils.isValidEmail(emailAddress)){
            result = Pair(false, "Email is invalid")
        }
        else if(!TextUtils.isEmpty(password) && password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }
}