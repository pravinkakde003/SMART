package com.user.smart.models

import java.io.Serializable

data class POSDetailsListItem(
    val quantity: String,
    val description: String,
    val amount: String
) : Serializable