package com.user.smart.models

import java.io.Serializable

data class TransactionLine(
    val FuelLine: FuelLine,
    val FuelPrepayLine: FuelPrepayLine,
    val ItemLine: ItemLine,
    val status: String
) : Serializable