package com.user.smart.models

import java.io.Serializable

data class ItemCode(
    val POSCode: String,
    val POSCodeFormat: POSCodeFormat,
    val POSCodeModifier: POSCodeModifier
) : Serializable