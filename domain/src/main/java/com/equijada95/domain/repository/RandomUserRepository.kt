package com.equijada95.domain.repository

import com.equijada95.data.model.RandomUserModel
import com.equijada95.data.provider.AppProvider
import com.equijada95.domain.model.User
import com.equijada95.heroapp.domain.result.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface RandomUserRepository {
    fun getUsers(): Flow<ApiResult<List<User>>>
}

class RandomUserRepositoryImpl @Inject constructor(
    private val provider: AppProvider
): RandomUserRepository {
    override fun getUsers(): Flow<ApiResult<List<User>>> = flow {
        val apiResponse = provider.getAll().body()
        emit(ApiResult.Success(apiResponse?.toUserList()))
    }
}

private fun List<RandomUserModel>.toUserList() : List<User> {
    val list = mutableListOf<User>()
    this.map { list.add(it.toUser()) }
    return list
}

private fun RandomUserModel.toUser() = User(
    name = "${name.title} " + "${name.first} " + name.last,
    email = this.email,
    gender = this.gender,
    latitude = location.coordinates.latitude,
    longitude = location.coordinates.longitude,
    picture = picture.thumbnail,
    registeredDate = registered.date
)