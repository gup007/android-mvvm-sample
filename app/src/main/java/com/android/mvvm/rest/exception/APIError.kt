package com.android.mvvm.rest.exception

import com.google.gson.annotations.SerializedName

/**
 * Created by Brijesh on 06/06/2019.
 * Custom Error object for server error
 */
class APIError {

    @SerializedName("name")
    val name: String? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("status")
    val statusCode: Int = 0

    @SerializedName("type")
    val type: String? = null

    @SerializedName("code")
    val code: Int = 0

}
