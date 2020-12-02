package com.android.mvvm.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvm.R
import com.android.mvvm.common.ImageCaching

class RecipeViewHolder(itemView: View) :
    BaseViewHolder(itemView) {

    private var tvRecipeTitle: TextView = itemView.findViewById(R.id.tv_recipe_title)
    private var tvRecipeHeadline: TextView = itemView.findViewById(R.id.tv_recipe_source)
    private var ivRecipePic: ImageView = itemView.findViewById(R.id.iv_recipe_pic)


    override fun onBind(response: Recipe) {
        super.onBind(response)
        response.imageUrl?.let {
            ImageCaching(itemView.context).load(it).into(ivRecipePic)
        }
        tvRecipeTitle.text = response.title
        tvRecipeHeadline.text = response.headLine
    }
}

class HeaderViewHolder(itemView: View) :
    BaseViewHolder(itemView) {

    private var tvHeader: TextView = itemView.findViewById(R.id.tv_current_date)

    override fun onBind(response: Recipe) {
        super.onBind(response)
        tvHeader.text = response.title
    }
}

open class BaseViewHolder(
    itemView: View
) :
    RecyclerView.ViewHolder(itemView) {

    open fun onBind(response: Recipe) {}
}
