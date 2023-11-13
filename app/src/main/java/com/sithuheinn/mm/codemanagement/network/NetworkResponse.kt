package com.sithuheinn.mm.codemanagement.network

import com.squareup.moshi.Json
import retrofit2.Response

data class NetworkResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
) {


    companion object {
        /**
         * Made network request successfully.
         * @param response network response
         */
        fun <T> success(response: Response<T>): NetworkResponse<T> {
            return NetworkResponse(
                status = Status.Success,
                data = response,
                exception = null
            )
        }

        /**
         * Failed network request.
         * @param exception Any threw exception
         */
        fun <T> failure(exception: Exception): NetworkResponse<T> {
            return NetworkResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }
    }

    /**
     * Boolean value to check out of control network issues.
     *
     * i.e Network Exception etc.,
     */
    val failed: Boolean
        get() = this.status == Status.Failure

    /**
     * Network request is successful
     */
    val isSuccessful: Boolean
        get() = !failed && this.data?.isSuccessful == true

    /**
     * Network response body
     */
    val body: T?
        get() = this.data?.body()

}

/**
 * Response Status
 */
sealed class Status {
    object Success: Status()
    object Failure: Status()
}

inline fun <T> wrapApiCall(apiCall: () -> Response<T>): NetworkResponse<T> {
    return try {
        NetworkResponse.success(apiCall.invoke())
    } catch (e: Exception) {
        NetworkResponse.failure(e)
    }
}

data class ErrorResponse(
    @field:Json(name = "message") val message: String
)