package com.user.smart.models

import java.io.Serializable

data class FuelPriceAPIResponseItem(
    val _id: String,
    val createdAt: String,
    val fueltax: Fueltax,
    val new_cash_price: String,
    val new_credit_price: String,
    val old_cash_price: String,
    val old_credit_price: String,
    val prepaid_sales_tax: String,
    val price_changes_date: String,
    val register_mapping: String,
    val store_fuel_department: String,
    val store_fuel_grade: String,
    val store_fuel_grade_display_name: String,
    val store_fuel_grade_id: String,
    val store_fuel_tax: String,
    val store_id: String,
    val tank_size: String,
    val tax_rate: String,
    val updatedAt: String,
    val ust_fees: String
) : Serializable