package com.user.smart.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.user.smart.R
import com.user.smart.models.DashboardMenuModel


class DashboardMenuListAdapter(
    var context: Context,
    dataList: List<DashboardMenuModel>,
    private val onClickListener: (View, DashboardMenuModel) -> Unit
) :
    RecyclerView.Adapter<DashboardMenuListAdapter.ViewHolder>() {

    private var dataList = emptyList<DashboardMenuModel>()
    var mContext: Context = context

    init {
        this.dataList = dataList
        this.mContext = context
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: MaterialTextView = itemView.findViewById(R.id.txtMenuName)
        var image: ShapeableImageView = itemView.findViewById(R.id.imageViewMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.dashboard_menu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.title.text = data.title
        if (null != data.imageRes) {
            val resourceId: Int =
                mContext.resources.getIdentifier(data.imageRes, "drawable", mContext.packageName)
            if (resourceId != 0) {
                holder.image.setImageResource(resourceId)
            }
        }
        holder.itemView.setOnClickListener { view ->
            onClickListener.invoke(view, data)
        }
    }

    override fun getItemCount() = dataList.size
}