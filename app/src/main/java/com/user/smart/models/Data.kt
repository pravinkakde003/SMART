package com.user.smart.models

data class Data(
    val FGM: List<FGM>,
    val FGMVol: List<FGMVol>,
    val FGM_total_amount: List<FGMTotalAmount>,
    val FGM_total_volume: List<FGMTotalVolume>,
    val MCM: List<MCM>,
    val MCM_total: List<MCMTotal>,
    val SalesTax: List<SalesTax>,
    val ZReading: String,
    val notes: String,
    val totalinamt: List<Totalinamt>
)