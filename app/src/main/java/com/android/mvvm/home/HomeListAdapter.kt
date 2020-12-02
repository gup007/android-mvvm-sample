package com.android.mvvm.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvm.R
import com.android.mvvm.common.BindableAdapter
import com.android.mvvm.common.RecyclerItemClickListener


class HomeListAdapter(
    private val recyclerView: RecyclerView,
    private val listener: RecyclerItemClickListener<Recipe>
) :
    RecyclerView.Adapter<BaseViewHolder>(),
    BindableAdapter<List<Recipe>>, View.OnClickListener {

    private var responseList: ArrayList<Recipe> = ArrayList()

    override fun getItemViewType(position: Int): Int {
        return responseList[position].viewType
    }

    override fun setData(data: List<Recipe>?) {
        responseList.clear()
        if (data != null) {
            responseList.addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.view_list_item_header, parent, false
            )
            return HeaderViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.view_list_item, parent, false
        )
        view.setOnClickListener(this)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return responseList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(responseList[position])
    }

    override fun onClick(view: View) {
        val childAdapterPosition = recyclerView.getChildAdapterPosition(view)
        if (childAdapterPosition < 0 || childAdapterPosition >= responseList.size) {
            return
        }
        listener.onItemClick(view, responseList[childAdapterPosition])
    }
}