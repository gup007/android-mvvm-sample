package com.android.mvvm.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AdapterSpaceDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val childLayoutPosition = parent.getChildLayoutPosition(view)
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            val gridLayoutManager = layoutManager as GridLayoutManager?
            val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
            val spanIndex = layoutParams.spanIndex
            val spanLookUpCount =
                gridLayoutManager!!.spanSizeLookup.getSpanSize(childLayoutPosition)
            outRect.top = space / 2
            outRect.bottom = space / 2
            if (spanLookUpCount > 1) {
                outRect.right = 0
                outRect.left = 0
            } else {
                outRect.right = if (spanIndex == 0) space / 2 else space / 4
                outRect.left = if (spanIndex == 1) space / 2 else space / 4
            }
        } else if (layoutManager is LinearLayoutManager) {
            outRect.top = if (childLayoutPosition == 0) space else 0
            outRect.bottom = space
        }
    }
}
