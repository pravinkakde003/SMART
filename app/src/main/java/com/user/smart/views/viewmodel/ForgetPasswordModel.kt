package com.user.smart.views.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.ForgetPasswordResponseModel
import com.user.smart.repository.LoginRepository
import com.user.smart.repository.NetworkResult
import com.user.smart.utils.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    val forgetPasswordLiveData: LiveData<NetworkResult<ForgetPasswordResponseModel>>
        get() = loginRepository.forgetPasswordLiveData


    fun validateCredentials(emailAddress: String): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(emailAddress)) {
            result = Pair(false, "Please enter email id")
        } else if (!AppUtils.isValidEmail(emailAddress)) {
            result = Pair(false, "Email is invalid")
        }
        return result
    }

    fun forgetPasswordAPI(emailAddress: String) {
        viewModelScope.launch {
            loginRepository.forgetPassword(emailAddress)
        }
    }
}