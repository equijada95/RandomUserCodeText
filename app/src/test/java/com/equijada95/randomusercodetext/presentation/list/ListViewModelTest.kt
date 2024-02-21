package com.equijada95.randomusercodetext.presentation.list

import app.cash.turbine.test
import com.equijada95.domain.model.Gender
import com.equijada95.domain.model.User
import com.equijada95.domain.repository.RandomUserRepository
import com.equijada95.domain.result.ApiResult
import com.equijada95.randomusercodetext.R
import com.equijada95.randomusercodetext.presentation.MainCoroutinesExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals

class ListViewModelTest {

    @RegisterExtension val coroutinesExtension = MainCoroutinesExtension()

    private lateinit var viewModel: ListViewModel

    private val repository = mockk<RandomUserRepository>(relaxed = true)

    private fun initViewModel() {
        viewModel = ListViewModel(repository)
    }

    private val mockUsers = listOf(
        User(gender = Gender.MALE, name = "Pablo Garcia", email = "pablo@gmail.com", latitude = "-69.8246", longitude = "134.8719", picture = "https://randomuser.me/api/portraits/men/75.jpg", registeredDate = "2007-07-09T05:51:59.390Z", phone = "(272) 790-0888"),
        User(gender = Gender.MALE, name = "Eugenio Fernandez", email = "eugenio@gmail.com", latitude = "-69.8246", longitude = "134.8719", picture = "https://randomuser.me/api/portraits/men/75.jpg", registeredDate = "2007-07-09T05:51:59.390Z", phone = "(272) 790-0888"),
        User(gender = Gender.MALE, name = "Paco Sánchez", email = "paco@gmail.com", latitude = "-69.8246", longitude = "134.8719", picture = "https://randomuser.me/api/portraits/men/75.jpg", registeredDate = "2007-07-09T05:51:59.390Z", phone = "(272) 790-0888"),
    )

    private val mockSearchUsers = listOf(
        User(
            gender = Gender.MALE,
            name = "Eugenio Fernandez",
            email = "eugenio@gmail.com",
            latitude = "-69.8246",
            longitude = "134.8719",
            picture = "https://randomuser.me/api/portraits/men/75.jpg",
            registeredDate = "2007-07-09T05:51:59.390Z",
            phone = "(272) 790-0888"
        )
    )

    private val mockApiSuccess = ApiResult.Success(mockUsers)

    @Test
    fun `get all users`() {
        coroutinesExtension.runTest {
            coEvery { repository.getUsers(any()) } returns flow { emit(mockApiSuccess) }
            initViewModel()
            viewModel.state.test {
                awaitItem()
                assertEquals(awaitItem().userList, mockUsers)
            }
        }
    }

    @Test
    fun `when repository returns server error, returns server error`() {
        coroutinesExtension.runTest {
            coEvery { repository.getUsers(any()) } returns flow { emit(ApiResult.Error(error = ApiResult.ApiError.SERVER_ERROR)) }
            initViewModel()
            viewModel.event.test {
                assertEquals(awaitItem().messageId, R.string.error_server)
            }
        }
    }

    @Test
    fun `when repository returns no connection error, returns no connection error`() {
        coroutinesExtension.runTest {
            coEvery { repository.getUsers(any()) } returns flow { emit(ApiResult.Error(error = ApiResult.ApiError.NO_CONNECTION_ERROR)) }
            initViewModel()
            viewModel.event.test {
                assertEquals(awaitItem().messageId, R.string.error_no_connection)
            }
        }
    }

    @Test
    fun `when searching for a user by name, returns the user with that name`() {
        coroutinesExtension.runTest {
            coEvery { repository.getUsers(any()) } returns flow { emit(mockApiSuccess) }
            initViewModel()
            viewModel.state.test {
                awaitItem()
                awaitItem()
                viewModel.search("Eugenio")
                assertEquals(awaitItem().userList, mockSearchUsers)
            }
        }
    }
}