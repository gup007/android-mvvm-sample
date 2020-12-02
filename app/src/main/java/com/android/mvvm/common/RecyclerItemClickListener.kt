package com.android.mvvm.common

import android.view.View

interface RecyclerItemClickListener<T> {

    fun onItemClick(view: View, item: T)

}
