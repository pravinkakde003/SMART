package com.user.smart.utils.piechartutils.legend

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.user.smart.R
import com.user.smart.utils.piechartutils.Slice

open class LegendAdapter : RecyclerView.Adapter<LegendItemViewHolder>() {

    private val items = mutableListOf<Slice>()
    var onItemClickListener: ((Slice?) -> Unit)? = null

    fun setup(items: List<Slice>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegendItemViewHolder {
        return LegendItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_legend, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LegendItemViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(items[position])
        }
    }
}

open class LegendItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var boundItem: Slice? = null
    open fun bind(slice: Slice) {
        boundItem = slice
        var sliceImageView: ImageView = itemView.findViewById(R.id.imageViewCircleIndicator)
        var sliceName: TextView = itemView.findViewById(R.id.textViewSliceTitle)
        sliceImageView.imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(itemView.context, slice.color))
        sliceName.text = slice.name
    }
}