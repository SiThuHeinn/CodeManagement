package com.sithuheinn.mm.codemanagement.network

import com.squareup.moshi.Moshi

abstract class NetworkResponseMapper<MapInput, R: NetworkResponse<MapInput>, MapOutput> {

    /**
     * Check network call has error or data.
     * @param res network response
     * @throws [exception] whenever there is network call errors or server returns errors.
     * @return validated network response
     */
    private fun errorOrData(res: R): R {

        if (res.failed) {
            throw res.exception!!
        }

        if (res.isSuccessful.not()) {
            val message = generateErrorMessage(res)
            when (res.data?.code()) {
                400 -> {
                    throw UnknownException("HTTP 400 Error! $message")
                }

                401 -> {
                    throw UnAuthorizedException("HTTP 401 Error! $message")
                }

                403 -> {
                    throw UnknownException("HTTP 403 Error! $message")
                }
                404 -> {
                    throw UnknownException("HTTP 404 Error! $message")
                }
                500 -> {
                    throw UnknownException("HTTP 500 Error! $message")
                }
                503 -> {
                    throw UnknownException("HTTP 503 Error! $message")

                }

            }
        }
        return res
    }

    protected abstract fun areResponseDataNotNull(res: R): Boolean
    protected abstract fun provideDataForMapping(res: R): MapInput
    protected abstract fun map(input: MapInput): MapOutput

    fun mapOrError(res: R): MapOutput {
        val validatedInput = errorOrData(res)
        if (areResponseDataNotNull(validatedInput)) {
            return map(provideDataForMapping(res))
        }

        if (res.body == null) {
            throw NoContentException("Response body is null!")
        }

        throw InvalidDataException("Invalid response data!")
    }

    /**
     * Generate error message from response errorBody()
     * @param res  network response
     * @return error message
     */
    private fun generateErrorMessage(res: R): String {
        val errorJsonString = res.data!!.errorBody()!!.byteStream().bufferedReader().use { it.readText() }
        return try {
            val error = Moshi.Builder()
                .build()
                .adapter(ErrorResponse::class.java)
                .fromJson(errorJsonString)
            error?.message.orEmpty()
        } catch (e: Exception) {
            errorJsonString
        }

    }

}