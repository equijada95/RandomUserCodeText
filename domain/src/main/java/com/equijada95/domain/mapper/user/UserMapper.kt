package com.equijada95.domain.mapper.user

import androidx.annotation.VisibleForTesting
import com.equijada95.data.model.RandomUserModel
import com.equijada95.data.model.RandomUserResults
import com.equijada95.domain.mapper.BaseMapper
import com.equijada95.domain.model.Gender
import com.equijada95.domain.model.User
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_FORMAT_INPUT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val DATE_FORMAT_OUTPUT = "dd-MM-yyyy"

class UserMapper: BaseMapper<RandomUserResults, List<User>> {
    override fun map(toMap: RandomUserResults?): List<User>? = toMap?.results?.toUserList()
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
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
        inputDate?.let { SimpleDateFormat(DATE_FORMAT_OUTPUT, Locale.ENGLISH).format(it) }
            ?: run { this }
    } catch (_: ParseException) {
        this
    }
}