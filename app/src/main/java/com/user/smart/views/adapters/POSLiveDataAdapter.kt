package com.user.smart.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.user.smart.databinding.TransactionLayoutItemBinding
import com.user.smart.models.FuelLine
import com.user.smart.models.POSLiveDataResponseItem

class POSLiveDataAdapter(
    private var transactionsItemList: List<POSLiveDataResponseItem>,
    private var onItemClicked: ((dataItem: POSLiveDataResponseItem) -> Unit)
) : RecyclerView.Adapter<POSLiveDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TransactionLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactionsItemList[position])
    }

    inner class ViewHolder(private val binding: TransactionLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: POSLiveDataResponseItem) = binding.apply {

            if (dataItem.EventEndDate.isNotBlank() && dataItem.EventEndTime.isNotBlank()) {
                val transactionDate = "${dataItem.EventEndDate} ${dataItem.EventEndTime}"
                txtTransactionDate.text = transactionDate
            }

            val dataItemPresent = dataItem.TransactionLine.any { it.FuelLine is FuelLine }
            if (dataItemPresent) {
                val itemFuelLine = dataItem.TransactionLine.find { it.FuelLine is FuelLine }
                if (!itemFuelLine?.FuelLine?.Description.isNullOrBlank()) {
                    txtTransactionType.text = itemFuelLine?.FuelLine?.Description
                }
            }

            if (dataItem.TransactionTotalGrossAmount.isNotBlank()) {
                val dollarAppendedAmount = "$" + dataItem.TransactionTotalGrossAmount
                txtAmount.text = dollarAppendedAmount
            }
            if (dataItem.CashierID.isNotBlank()) {
                txtCashier.text = dataItem.CashierID
            }

            root.setOnClickListener {
                onItemClicked(dataItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return transactionsItemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}