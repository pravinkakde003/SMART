package com.user.smart.models

import java.io.Serializable

data class SalesRestrictFlag(
    val type: String,
    val value: String
) : Serializable