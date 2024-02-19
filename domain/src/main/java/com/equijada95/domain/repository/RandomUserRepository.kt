package com.equijada95.domain.repository

import com.equijada95.data.model.RandomUserModel
import com.equijada95.data.provider.AppProvider
import com.equijada95.domain.model.User
import com.equijada95.domain.result.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface RandomUserRepository {
    fun getUsers(): Flow<ApiResult<List<User>>>
}

class RandomUserRepositoryImpl @Inject constructor(
    private val provider: AppProvider
): RandomUserRepository {
    override fun getUsers(): Flow<ApiResult<List<User>>> = flow {
        try {
            val apiResponse = provider.getAll().body()
            emit(ApiResult.Success(apiResponse?.results?.toUserList()))
        } catch (e: HttpException) {
            emit(ApiResult.Error(
                error = ApiResult.ApiError.SERVER_ERROR,
            ))
        } catch (e: IOException) {
            emit(ApiResult.Error(
                error = ApiResult.ApiError.NO_CONNECTION_ERROR,
            ))
        }
    }
}

private fun List<RandomUserModel>.toUserList() = map { it.toUser() }

private fun RandomUserModel.toUser() = User(
    name = "${name.first} " + name.last,
    email = this.email,
    gender = this.gender,
    latitude = location.coordinates.latitude,
    longitude = location.coordinates.longitude,
    picture = picture.large,
    registeredDate = registered.date
)