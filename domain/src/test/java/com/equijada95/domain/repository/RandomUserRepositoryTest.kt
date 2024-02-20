package com.equijada95.domain.repository

import app.cash.turbine.test
import com.equijada95.data.model.NameModel
import com.equijada95.data.model.PictureModel
import com.equijada95.data.model.RandomUserModel
import com.equijada95.data.model.RandomUserResults
import com.equijada95.data.model.RegisteredModel
import com.equijada95.data.model.location.CoordinatesModel
import com.equijada95.data.model.location.LocationModel
import com.equijada95.data.provider.AppProvider
import com.equijada95.domain.result.ApiResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertEquals

class RandomUserRepositoryTest {

    private val provider: AppProvider = mockk(relaxed = true)

    private val repository = RandomUserRepositoryImpl(provider)

    private val mockName = NameModel(
        first = "Nacho",
        last = "Garcia"
    )

    private val mockLocation = LocationModel(
        CoordinatesModel(
            latitude = "-69.8246",
            longitude = "134.8719",
        )
    )

    private val mockPicture = PictureModel(
        large = "https://randomuser.me/api/portraits/men/75.jpg"
    )

    private val mockRegistered = RegisteredModel(
        date = "2007-07-09T05:51:59.390Z"
    )

    private val mockUsers = RandomUserResults(
        results = listOf(
            RandomUserModel(
                gender = "female",
                name = mockName,
                email = "pablo@gmail.com",
                location = mockLocation,
                picture = mockPicture,
                registered = mockRegistered,
                phone = "(272) 790-0888"
            ),
            RandomUserModel(
                gender = "female",
                name = mockName,
                email = "pablo@gmail.com",
                location = mockLocation,
                picture = mockPicture,
                registered = mockRegistered,
                phone = "(272) 790-0888"
            ),
        )
    )

    private val ioException = mockk<IOException>(relaxed = true)

    @Test
    fun `get all users`() {
        coEvery { provider.getResults(any()).body() } returns mockUsers

        runBlocking {
            repository.getUsers(2).test {
                val response = awaitItem()
                assert(response is ApiResult.Success)
                assertEquals(response.data, mockUsers.results.toUserList())
                coVerify { provider.getResults(any()) }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `when an server error occurs, returns an error`() {
        coEvery { provider.getResults(any()) } returns Response.error(404, mockk<ResponseBody>(relaxed = true))

        runBlocking {
            repository.getUsers(2).test {
                val response = awaitItem()
                assert(response is ApiResult.Error)
                assertEquals(response.error, ApiResult.ApiError.SERVER_ERROR)
                coVerify { provider.getResults(any()) }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `when a no connection error occurs, returns an error`() {
        coEvery { provider.getResults(any()).body() } throws ioException

        runBlocking {
            repository.getUsers(2).test {
                val response = awaitItem()
                assert(response is ApiResult.Error)
                assertEquals(response.error, ApiResult.ApiError.NO_CONNECTION_ERROR)
                coVerify { provider.getResults(any()) }
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}