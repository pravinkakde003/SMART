package com.user.smart.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.user.smart.databinding.GroupListItemBinding
import com.user.smart.models.GroupsListDataItem

class GroupsDataAdapter(
    private var groupsDataItemList: List<GroupsListDataItem>,
) : RecyclerView.Adapter<GroupsDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GroupListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(groupsDataItemList[position])
    }

    inner class ViewHolder(private val binding: GroupListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: GroupsListDataItem) = binding.apply {

            if (dataItem.price_group.isNotBlank()) {
                txtGroupName.text = dataItem.price_group
            }

            if (dataItem.items.isNotBlank()) {
                txtGroupItems.text = dataItem.items
            }
        }
    }

    override fun getItemCount(): Int {
        return groupsDataItemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}