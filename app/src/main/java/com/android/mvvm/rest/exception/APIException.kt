package com.android.mvvm.rest.exception


import com.android.mvvm.rest.APIResponse
import java.io.IOException


class APIException : Exception {
    val kind: Kind
    val code: Int
    val error: APIError?
    var response: APIResponse? = null
    var trackUrl: String? = null

    constructor(kind: Kind, msg: String) : this(kind, -1, msg, null) {}

    constructor(kind: Kind, msg: String, cause: Throwable) : this(kind, -1, msg, null, cause) {}

    @JvmOverloads
    constructor(kind: Kind, code: Int, msg: String, error: APIError? = null) : super(msg) {
        this.kind = kind
        this.code = code
        this.error = error
    }

    constructor(
        kind: Kind,
        code: Int,
        msg: String,
        trackUrl: String,
        error: APIError? = null
    ) : super(msg) {
        this.kind = kind
        this.code = code
        this.error = error
        this.trackUrl = trackUrl
    }

    @JvmOverloads
    constructor(
        kind: Kind,
        code: Int,
        msg: String,
        response: APIResponse,
        error: APIError? = null
    ) : super(msg) {
        this.kind = kind
        this.code = code
        this.error = error
        this.response = response
    }

    constructor(kind: Kind, code: Int, msg: String, error: APIError?, cause: Throwable) : super(
        msg,
        cause
    ) {
        this.kind = kind
        this.code = code
        this.error = error
    }

    fun hasError(): Boolean {
        return this.error != null
    }

    /**
     * Identifies the event kind which triggered a [APIException].
     */
    enum class Kind {
        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,
        /**
         * An exception was thrown while (de)serializing a body.
         */
        CONVERSION,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,

        /**
         * call back is null
         */
        NULL,

        /**
         *
         */
        SOCKET_TIMEOUT,
        /**
         * Exception kind for invalid request
         */
        INVALID_REQUEST,

        /**
         * Exception kind for invalid response
         */
        INVALID_RESPONSE,

        /**
         * Exception for incompatible data cache version
         */
        CACHE_EXCEPTION,

        /**
         * invalid auth url for auth enabled apis
         */
        INVALID_AUTH_URL,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED,
        /**
         * Forcefully stopped for showing some alert
         */
        CASHIFY_ALERT,
        /**
         * For tracking the API
         */
        TRACK_URL
    }
}
