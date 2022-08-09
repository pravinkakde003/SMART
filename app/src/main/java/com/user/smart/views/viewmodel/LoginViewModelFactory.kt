package com.user.smart.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.user.smart.repository.LoginRepository

class LoginViewModelFactory(private val loginRepository: LoginRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}