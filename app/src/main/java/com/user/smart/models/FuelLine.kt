package com.user.smart.models

data class FuelLine(
    val ActualSalesPrice: String,
    val Description: String,
    val EntryMethod: EntryMethod,
    val FuelGradeID: String,
    val FuelPositionID: String,
    val ItemTax: ItemTax,
    val MerchandiseCode: String,
    val PriceTierCode: String,
    val RegularSellPrice: String,
    val SalesAmount: String,
    val SalesQuantity: String,
    val ServiceLevelCode: String,
    val TimeTierCode: String
)