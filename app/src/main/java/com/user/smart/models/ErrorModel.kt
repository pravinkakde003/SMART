package com.user.smart.models

import java.io.Serializable

data class ErrorModel(
    val message: String,
    val statusCode: Int
) : Serializable