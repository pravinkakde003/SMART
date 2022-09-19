package com.user.smart.models

data class TenderInfo(
    val Authorization: Authorization,
    val ChangeFlag: ChangeFlag,
    val Tender: Tender,
    val TenderAmount: String
)