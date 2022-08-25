package com.user.smart.views.viewmodel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.user.smart.repository.LoginRepository
import com.user.smart.utils.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    fun validateCredentials(emailAddress: String): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(emailAddress)) {
            result = Pair(false, "Please enter email id")
        } else if (!AppUtils.isValidEmail(emailAddress)) {
            result = Pair(false, "Email is invalid")
        }
        return result
    }
}