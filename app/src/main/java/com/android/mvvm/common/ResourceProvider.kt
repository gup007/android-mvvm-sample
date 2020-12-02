package com.android.mvvm.common

import android.content.Context
import androidx.core.content.ContextCompat
import javax.inject.Inject

class ResourceProvider @Inject constructor(val mContext: Context) {

    fun getString(resId: Int): String {
        return mContext.getString(resId)
    }

    fun getString(resId: Int, value: String): String {
        return mContext.getString(resId, value)
    }

    fun getColor(colorCode: Int): Int {
        return ContextCompat.getColor(mContext, colorCode)
    }

}