package com.user.smart.models

data class TransactionLine(
    val status: String,
    val FuelLine: FuelLine,
    val ItemLine: ItemLine,
    val MerchandiseCodeLine: MerchandiseCodeLine,
    val TenderInfo: TenderInfo,
    val TransactionTax: TransactionTax
)