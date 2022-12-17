package com.user.smart.models

data class Data(
    val In: In,
    val Out: Out,
    val ZReading: String,
    val notes: String,
    val totalOutamt: List<Any>,
    val totalinamt: List<Totalinamt>
)