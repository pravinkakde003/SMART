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


class DashboardMenuListAdapter(var context: Context) :
    RecyclerView.Adapter<DashboardMenuListAdapter.ViewHolder>() {

    private var dataList = emptyList<DashboardMenuModel>()
    var mContext: Context = context

    fun setLisData(dataList: List<DashboardMenuModel>){
        this.dataList = dataList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: MaterialTextView
        var image: ShapeableImageView

        init {
            title = itemView.findViewById(R.id.txtMenuName)
            image = itemView.findViewById(R.id.imageViewMenu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.dashboard_menu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = dataList[position]
        holder.title.text = data.title
//        holder.image.setImageResource(data.imageRes)
    }

    override fun getItemCount() = dataList.size
}