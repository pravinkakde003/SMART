package com.user.smart.models

data class MerchandiseCodeLine(
    val ActualSalesPrice: String,
    val Description: String,
    val ItemTax: ItemTax,
    val MerchandiseCode: String,
    val RegularSellPrice: String,
    val SalesAmount: String,
    val SalesQuantity: String,
    val SalesRestriction: Any
)