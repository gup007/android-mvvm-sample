package com.android.mvvm.rest.callback


import com.android.mvvm.rest.exception.APIError
import com.android.mvvm.rest.exception.APIException
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException


abstract class APICallback<T> : ICallback<T> {


    override fun onResponse(call: Call<T>, response: Response<T>?) {
        onComplete()
        val status = response?.code() ?: APIException.Kind.UNEXPECTED.ordinal

        if (response == null) {
            onFailure(APIException(APIException.Kind.INVALID_RESPONSE, "response == null"))
            return
        }

        if (!response.isSuccessful) {
            onFailure(getAPIException(response, null))
            return
        }

        val apiResponse = response.body()
        if (apiResponse == null) {
            onFailure(APIException(APIException.Kind.NULL, status, "IResponse can't be null"))
            return
        }
        onSuccess(apiResponse, response.raw())
    }

    override fun onFailure(call: Call<T>, cause: Throwable?) {
        onComplete()
        val apiException = getAPIException(null, cause)
        onFailure(apiException)
    }

    private fun getAPIException(response: Response<T>?, cause: Throwable?): APIException {
        if (cause != null) {
            return if (cause is SocketTimeoutException) {
                APIException(APIException.Kind.SOCKET_TIMEOUT, "Time Out", cause)
            } else if (cause is JSONException) {
                APIException(APIException.Kind.CONVERSION, "Conversion Error", cause)
            } else if (cause is IOException) {
                APIException(APIException.Kind.NETWORK, "Network Error", cause)
            } else cause as? APIException
                ?: APIException(APIException.Kind.UNEXPECTED, "Unknown", cause)
        } else if (response != null) {
            val code = response.code()
            val responseMsg = response.message()
            val errorBody = response.errorBody()
            val apiError = parseApiError(errorBody)
            return APIException(APIException.Kind.HTTP, code, responseMsg, apiError)
        }
        return APIException(APIException.Kind.UNEXPECTED, "Unknown Error")
    }

    private fun parseApiError(error: ResponseBody?): APIError? {
        if (null == error) {
            return null
        }
        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            return gson.fromJson(error.string(), APIError::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
