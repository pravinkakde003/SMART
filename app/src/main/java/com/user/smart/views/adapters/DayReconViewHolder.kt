package com.user.smart.views.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.user.smart.databinding.HeaderLayoutItemBinding
import com.user.smart.databinding.SubHeaderLayoutItemBinding
import com.user.smart.databinding.TotalLayoutItemBinding
import com.user.smart.databinding.TransactionItemTwoBinding

sealed class DayReconViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: HeaderLayoutItemBinding) :
        DayReconViewHolder(binding) {
        fun bind(title: DayReconViewItem.Title) {
            binding.txtHeader.text = title.title
        }
    }

    class SubTitleViewHolder(private val binding: SubHeaderLayoutItemBinding) :
        DayReconViewHolder(binding) {
        fun bind(subTitle: DayReconViewItem.SubTitle) {
            binding.txtSubHeaderOne.text = subTitle.subTitleOne
            binding.txtSubHeaderTwo.text = subTitle.subTitleTwo
            binding.txtSubHeaderThree.text = subTitle.subTitleThree
        }
    }

    class ListDataItemViewHolder(private val binding: TransactionItemTwoBinding) :
        DayReconViewHolder(binding) {
        fun bind(transactionItem: DayReconViewItem.ListDataItem) {
            binding.txtItemName.text = transactionItem.itemName
            binding.txtItemSold.text = transactionItem.quantity
            binding.txtItemAmount.text = "$${transactionItem.amount}"
        }
    }

    class TotalViewHolder(private val binding: TotalLayoutItemBinding) :
        DayReconViewHolder(binding) {
        fun bind(total: DayReconViewItem.Total) {
            binding.txtSubHeaderOne.text = total.totalTitleOne
            binding.txtSubHeaderTwo.text = total.totalTitleTwo
            binding.txtSubHeaderThree.text = "$${total.totalTitleThree}"
        }
    }
}