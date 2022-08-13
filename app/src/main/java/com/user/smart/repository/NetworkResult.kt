package com.user.smart.repository

import com.user.smart.models.ErrorModel
import okhttp3.ResponseBody

sealed class NetworkResult<T>(val data: T? = null, val errorMessage: ErrorModel? = null) {
    class Loading<T> : NetworkResult<T>()
    class Success<T>(data: T? = null) : NetworkResult<T>(data = data)
    class Error<T>(errorMessage: ErrorModel?) : NetworkResult<T>(errorMessage = errorMessage)
}