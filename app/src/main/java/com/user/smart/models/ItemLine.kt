package com.user.smart.models

data class ItemLine(
    val ActualSalesPrice: String,
    val Description: String,
    val EntryMethod: EntryMethod,
    val ItemCode: ItemCode,
    val ItemTax: ItemTax,
    val MerchandiseCode: String,
    val RegularSellPrice: String,
    val SalesAmount: String,
    val SalesQuantity: String,
    val SalesRestriction: SalesRestriction,
    val SellingUnits: String
)