package com.equijada95.domain.repository

import com.equijada95.data.model.RandomUserModel
import com.equijada95.data.provider.AppProvider
import com.equijada95.domain.model.Gender
import com.equijada95.domain.model.User
import com.equijada95.domain.result.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

private const val DATE_FORMAT_INPUT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val DATE_FORMAT_OUTPUT = "dd-MM-yyyy"

interface RandomUserRepository {
    fun getUsers(results: Int): Flow<ApiResult<List<User>>>
}

class RandomUserRepositoryImpl @Inject constructor(
    private val provider: AppProvider
): RandomUserRepository {
    override fun getUsers(results: Int): Flow<ApiResult<List<User>>> = flow {
        try {
            val response = provider.getResults(results)
            if (response.code() in 400..500) {
                emit(
                    ApiResult.Error(
                        error = ApiResult.ApiError.SERVER_ERROR,
                    )
                )
            } else {
                emit(ApiResult.Success(response.body()?.results?.toUserList()))
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

fun List<RandomUserModel>.toUserList() = map { it.toUser() }

private fun RandomUserModel.toUser() = User(
    name = "${name.first} " + name.last,
    email = this.email,
    gender = Gender.from(this.gender),
    latitude = location.coordinates.latitude,
    longitude = location.coordinates.longitude,
    picture = picture.large,
    registeredDate = registered.date.formatDate(),
    phone = phone
)

private fun String.formatDate(): String {
    return try {
        val inputDate = SimpleDateFormat(DATE_FORMAT_INPUT, Locale.ENGLISH).parse(this)
        inputDate?.let { SimpleDateFormat(DATE_FORMAT_OUTPUT, Locale.ENGLISH).format(it) } ?: run { this }
    } catch (_: ParseException) {
        this
    }

}