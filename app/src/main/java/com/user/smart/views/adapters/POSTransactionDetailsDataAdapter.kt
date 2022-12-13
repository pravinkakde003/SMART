package com.user.smart.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.user.smart.databinding.PosTransactionDetailsItemBinding
import com.user.smart.models.POSDetailsListItem

class POSTransactionDetailsDataAdapter(
    private var transactionsItemList: List<POSDetailsListItem>,
) : RecyclerView.Adapter<POSTransactionDetailsDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PosTransactionDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactionsItemList[position])
    }

    inner class ViewHolder(private val binding: PosTransactionDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: POSDetailsListItem) = binding.apply {

            if (dataItem.description.isNotBlank()) {
                txtDescription.text = dataItem.description
            }

            if (dataItem.quantity.isNotBlank()) {
                txtQuantity.text = dataItem.quantity
            }

            if (dataItem.amount.isNotBlank()) {
                txtAmount.text = "$${dataItem.amount}"
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