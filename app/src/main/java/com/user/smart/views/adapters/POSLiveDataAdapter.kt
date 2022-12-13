package com.user.smart.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.user.smart.databinding.PosLiveListLayoutItemBinding
import com.user.smart.models.POSLiveDataResponseItem

class POSLiveDataAdapter(
    private var transactionsItemList: List<POSLiveDataResponseItem>,
    private var storeName: String,
    private var onItemClicked: ((dataItem: POSLiveDataResponseItem) -> Unit)
) : RecyclerView.Adapter<POSLiveDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PosLiveListLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactionsItemList[position])
    }

    inner class ViewHolder(private val binding: PosLiveListLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: POSLiveDataResponseItem) = binding.apply {

            if (storeName.isNotBlank()) {
                txtStoreName.text = storeName
            }

            if (dataItem.EventEndDate.isNotBlank() && dataItem.EventEndTime.isNotBlank()) {
                val transactionDate = "${dataItem.EventEndDate} ${dataItem.EventEndTime}"
                txtTransactionDate.text = transactionDate
            }

            if (dataItem.TransactionTotalNetAmount.isNotBlank()) {
                val dollarAppendedAmount = "$" + dataItem.TransactionTotalNetAmount
                txtTransactionAmount.text = dollarAppendedAmount
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