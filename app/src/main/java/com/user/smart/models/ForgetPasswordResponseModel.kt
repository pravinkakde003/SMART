package com.user.smart.models

data class ForgetPasswordResponseModel(
    val additionalValidations: List<Any>,
    val message: String,
    val path: String,
    val statusCode: Int,
    val timestamp: String
)