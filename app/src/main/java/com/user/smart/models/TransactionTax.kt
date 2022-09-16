package com.user.smart.models

data class TransactionTax(
    val TaxCollectedAmount: String,
    val TaxExemptSalesAmount: String,
    val TaxExemptSalesRefundedAmount: String,
    val TaxForgivenAmount: String,
    val TaxForgivenSalesAmount: String,
    val TaxForgivenSalesRefundedAmount: String,
    val TaxLevelID: String,
    val TaxRefundedAmount: String,
    val TaxableSalesAmount: String,
    val TaxableSalesRefundedAmount: String
)