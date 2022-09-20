package com.user.smart.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.user.smart.databinding.TransactionDetailsItemBinding
import com.user.smart.models.POSDetailsListItem

class POSTransactionDetailsDataAdapter(
    private var transactionsItemList: List<POSDetailsListItem>,
) : RecyclerView.Adapter<POSTransactionDetailsDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TransactionDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactionsItemList[position])
    }

    inner class ViewHolder(private val binding: TransactionDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: POSDetailsListItem) = binding.apply {

            if (dataItem.quantity.isNotBlank()) {
                txtQuantity.text = dataItem.quantity
            }

            if (dataItem.upc.isNotBlank()) {
                txtUpc.text = dataItem.upc
            }

            if (dataItem.dept.isNotBlank()) {
                txtDept.text = dataItem.dept
            }

            if (dataItem.description.isNotBlank()) {
                txtDesc.text = dataItem.description
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