package com.user.smart.models

data class TransactionLine(
    val FuelLine: FuelLine,
    val FuelPrepayLine: FuelPrepayLine,
    val ItemLine: ItemLine,
    val status: String
)