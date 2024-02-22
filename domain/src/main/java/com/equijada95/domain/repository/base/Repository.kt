package com.equijada95.domain.repository.base

import com.equijada95.domain.mapper.BaseMapper
import com.equijada95.domain.result.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

interface Repository<T, P, K: BaseMapper<T, P>> {

    fun handleResult(response: Response<T>, mapper: BaseMapper<T, P>): Flow<ApiResult<P>> = flow {
        try {
            if (response.code() in 400..500) {
                emit(
                    ApiResult.Error(
                        error = ApiResult.ApiError.SERVER_ERROR,
                    )
                )
            } else {
                emit(ApiResult.Success(mapper.map(response.body())))
            }
        } catch (e: IOException) {
            emit(
                ApiResult.Error(
                    error = ApiResult.ApiError.NO_CONNECTION_ERROR,
                )
            )
        }
    }
}