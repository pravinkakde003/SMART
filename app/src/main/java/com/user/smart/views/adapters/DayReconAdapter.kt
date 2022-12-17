package com.user.smart.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.user.smart.R
import com.user.smart.databinding.HeaderLayoutItemBinding
import com.user.smart.databinding.SubHeaderLayoutItemBinding
import com.user.smart.databinding.TotalLayoutItemBinding
import com.user.smart.databinding.TransactionItemTwoBinding

class DayReconAdapter : RecyclerView.Adapter<DayReconViewHolder>() {

    var items = listOf<DayReconViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayReconViewHolder {
        return when (viewType) {
            R.layout.header_layout_item -> DayReconViewHolder.TitleViewHolder(
                HeaderLayoutItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.sub_header_layout_item -> DayReconViewHolder.SubTitleViewHolder(
                SubHeaderLayoutItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.transaction_item_two -> DayReconViewHolder.ListDataItemViewHolder(
                TransactionItemTwoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.total_layout_item -> DayReconViewHolder.TotalViewHolder(
                TotalLayoutItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: DayReconViewHolder, position: Int) {
        when (holder) {
            is DayReconViewHolder.TitleViewHolder -> holder.bind(items[position] as DayReconViewItem.Title)
            is DayReconViewHolder.SubTitleViewHolder -> holder.bind(items[position] as DayReconViewItem.SubTitle)
            is DayReconViewHolder.ListDataItemViewHolder -> holder.bind(items[position] as DayReconViewItem.ListDataItem)
            is DayReconViewHolder.TotalViewHolder -> holder.bind(items[position] as DayReconViewItem.Total)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DayReconViewItem.Title -> R.layout.header_layout_item
            is DayReconViewItem.SubTitle -> R.layout.sub_header_layout_item
            is DayReconViewItem.ListDataItem -> R.layout.transaction_item_two
            is DayReconViewItem.Total -> R.layout.total_layout_item
        }
    }
}