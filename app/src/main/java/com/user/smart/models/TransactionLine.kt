package com.user.smart.models

data class TransactionLine(
    val status: String,
    val ItemLine: ItemLine,
    val TenderInfo: TenderInfo,
    val TransactionTax: TransactionTax
)