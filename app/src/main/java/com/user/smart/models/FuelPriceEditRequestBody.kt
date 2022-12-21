package com.user.smart.models

data class FuelPriceEditRequestBody(
    val new_credit_price: String,
    val new_cash_price: String,
    val _id: String
)
