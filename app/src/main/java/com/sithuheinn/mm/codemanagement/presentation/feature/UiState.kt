package com.sithuheinn.mm.codemanagement.presentation.feature

sealed class UiState<T>(val data: T? = null, val message: String? = null) {

    class Nothing<T> : UiState<T>()

    class Loading<T>(message: String? = null) : UiState<T>(message = message)

    /**
     * Success Sate
     * @param data response data
     * @param message nullable message
     */
    class Success<T>(data: T, message: String? = null) : UiState<T>(data = data, message = message)

    /**
     * Error State
     * @param error error response
     */
    class Error<T>(error: String?) : UiState<T>(message = error)
}