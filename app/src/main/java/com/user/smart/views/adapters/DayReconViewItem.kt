package com.user.smart.views.adapters

sealed class DayReconViewItem {
    class Title(
        val title: String
    ) : DayReconViewItem()

    class SubTitle(
        val subTitleOne: String,
        val subTitleTwo: String,
        val subTitleThree: String
    ) : DayReconViewItem()

    class ListDataItem(
        val itemName: String,
        val quantity: String,
        val amount: String
    ) : DayReconViewItem()

    class Total(
        val totalTitleOne: String,
        val totalTitleTwo: String,
        val totalTitleThree: String
    ) : DayReconViewItem()
}