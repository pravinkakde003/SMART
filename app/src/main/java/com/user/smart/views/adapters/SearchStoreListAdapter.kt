package com.user.smart.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.user.smart.databinding.SearchStoreListItemBinding
import com.user.smart.models.StoreListModelItem


class SearchStoreListAdapter(
    private var storeList: List<StoreListModelItem>,
    private var onItemClicked: ((dataItem: StoreListModelItem) -> Unit)
) : RecyclerView.Adapter<SearchStoreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SearchStoreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storeList[position])
    }

    inner class ViewHolder(private val binding: SearchStoreListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataItem: StoreListModelItem) = binding.apply {
            if (!dataItem.store_name.isNullOrBlank()) {
                txtStoreName.text = dataItem.store_name
            }
            if (!dataItem.store_address.isNullOrBlank()) {
                txtStoreAddress.text = dataItem.store_address
            }

            root.setOnClickListener {
                onItemClicked(dataItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return storeList.size
    }
}