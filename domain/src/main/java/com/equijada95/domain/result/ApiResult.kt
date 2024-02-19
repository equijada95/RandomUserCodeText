package com.equijada95.heroapp.domain.result

sealed class ApiResult<T>(val data: T? = null, val error: ApiError? = null) {
    class Loading<T>(data: T? = null): ApiResult<T>(data)
    class Success<T>(data: T?): ApiResult<T>(data)
    class Error<T>(error: ApiError, data: T? = null): ApiResult<T>(data, error)

    enum class ApiError {
        SERVER_ERROR,
        NO_CONNECTION_ERROR,
        NO_ERROR
    }
}
