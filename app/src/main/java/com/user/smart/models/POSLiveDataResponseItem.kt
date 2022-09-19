package com.user.smart.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class POSLiveDataResponseItem(
    val CashierID: String,
    val EventEndDate: String,
    val EventEndTime: String,
    val EventType: String,
    val RegisterID: String,
    val TransactionID: String,
    val TransactionLine: @RawValue List<TransactionLine>,
    val TransactionTotalGrossAmount: String,
    val TransactionTotalNetAmount: String,
    val TransactionTotalTaxNetAmount: String
): Parcelable