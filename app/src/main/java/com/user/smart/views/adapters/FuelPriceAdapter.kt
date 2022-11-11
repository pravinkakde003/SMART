package com.user.smart.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.user.smart.databinding.FuelPriceLayoutItemBinding
import com.user.smart.models.FuelPriceAPIResponseItem

class FuelPriceAdapter(
    private var transactionsItemList: List<FuelPriceAPIResponseItem>,
    private var onItemClicked: ((dataItem: FuelPriceAPIResponseItem) -> Unit)
) : RecyclerView.Adapter<FuelPriceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FuelPriceLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactionsItemList[position])
    }

    inner class ViewHolder(private val binding: FuelPriceLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: FuelPriceAPIResponseItem) = binding.apply {

            if (dataItem.store_fuel_grade.isNotBlank()) {
                txtFuelGradeName.text = dataItem.store_fuel_grade_display_name
            }

            if (dataItem.old_cash_price.isNotBlank()) {
                txtCurrentCashPrice.text = dataItem.old_cash_price
            }

            if (dataItem.new_cash_price.isNotBlank()) {
                txtNewCashPrice.text = dataItem.new_cash_price
            }

            if (dataItem.old_credit_price.isNotBlank()) {
                txtCurrentCreditPrice.text = dataItem.old_credit_price
            }

            if (dataItem.new_credit_price.isNotBlank()) {
                txtNewCreditPrice.text = dataItem.new_credit_price
            }

            if (dataItem.updatedAt.isNotBlank()) {
                val convertedDate = dataItem.updatedAt
                txtLastModified.text = convertedDate
            }

            editButton.setOnClickListener {
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