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

            if (dataItem.store_fuel_grade_id.isNotBlank()) {
                txtFuelGradeNo.text = dataItem.store_fuel_grade_id
            }

            if (dataItem.store_fuel_grade.isNotBlank()) {
                txtFuelGradeName.text = dataItem.store_fuel_grade
            }

            if (dataItem.store_fuel_department.isNotBlank()) {
                txtFuelDepartment.text = dataItem.store_fuel_department
            }

            if (dataItem.tank_size.isNotBlank()) {
                txtTankSize.text = dataItem.tank_size
            }

            if (dataItem.fueltax.store_fuel_tax_description.isNotBlank()) {
                txtTax.text = dataItem.fueltax.store_fuel_tax_description
            }

            if (dataItem.prepaid_sales_tax.isNotBlank()) {
                txtPrePaidSalesTax.text = dataItem.prepaid_sales_tax
            }

            if (dataItem.tax_rate.isNotBlank()) {
                txtTaxRate.text = dataItem.tax_rate
            }

            if (dataItem.ust_fees.isNotBlank()) {
                txtUstFee.text = dataItem.ust_fees
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