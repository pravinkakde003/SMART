package com.user.smart.models

import java.io.Serializable

data class ForgetPasswordResponseModel(
    val additionalValidations: List<Any>,
    val message: String,
    val path: String,
    val statusCode: Int,
    val timestamp: String
) : Serializable