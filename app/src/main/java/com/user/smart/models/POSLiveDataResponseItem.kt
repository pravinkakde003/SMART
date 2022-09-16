package com.user.smart.models

data class POSLiveDataResponseItem(
    val CashierID: String,
    val EventEndDate: String,
    val EventEndTime: String,
    val EventType: String,
    val RegisterID: String,
    val TransactionID: String,
    val TransactionLine: List<TransactionLine>,
    val TransactionTotalGrossAmount: String,
    val TransactionTotalNetAmount: String,
    val TransactionTotalTaxNetAmount: String
)