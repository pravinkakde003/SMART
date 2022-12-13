package com.user.smart.models

data class TenderInfo(
    val AccountInfo: AccountInfo,
    val Authorization: Authorization,
    val ChangeFlag: ChangeFlag,
    val Tender: Tender,
    val TenderAmount: String
)