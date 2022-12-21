package com.user.smart.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.user.smart.databinding.ActiveLotteryLayoutItemBinding
import com.user.smart.models.ActiveLotteryResponse
import com.user.smart.models.ActiveLotteryResponseItem
import com.user.smart.utils.AppUtils

class ActiveLotteryAdapter(
    private var transactionsItemList: ActiveLotteryResponse,
    private var onItemClicked: ((dataItem: ActiveLotteryResponseItem) -> Unit)
) : RecyclerView.Adapter<ActiveLotteryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActiveLotteryLayoutItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactionsItemList[position])
    }

    inner class ViewHolder(private val binding: ActiveLotteryLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: ActiveLotteryResponseItem) = binding.apply {

            if (!dataItem.activate_date.isNullOrEmpty()) {
                txtActivateDate.text = AppUtils.formatDisplayDate(dataItem.activate_date)
            }

            if (!dataItem.roll_number.isNullOrEmpty()) {
                txtRollNo.text = dataItem.roll_number
            }

            if (!dataItem.deal_number.isNullOrEmpty()) {
                txtDealNo.text = dataItem.deal_number
            }

            if (!dataItem.no_of_tickets.isNullOrEmpty()) {
                txtNoTickets.text = dataItem.no_of_tickets
            }

            if (!dataItem.status.isNullOrEmpty()) {
                txtActivate.text = dataItem.status
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