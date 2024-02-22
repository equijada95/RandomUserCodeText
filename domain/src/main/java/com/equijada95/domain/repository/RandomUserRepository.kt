package com.equijada95.domain.repository

import com.equijada95.data.model.RandomUserResults
import com.equijada95.data.provider.AppProvider
import com.equijada95.domain.mapper.user.UserMapper
import com.equijada95.domain.model.User
import com.equijada95.domain.repository.base.Repository
import com.equijada95.domain.repository.base.RepositoryImpl
import com.equijada95.domain.result.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface RandomUserRepository {
    fun getUsers(results: Int): Flow<ApiResult<List<User>>>
}

class RandomUserRepositoryImpl @Inject constructor(
    private val provider: AppProvider,
    private val mapper: UserMapper
): RandomUserRepository, RepositoryImpl<RandomUserResults, List<User>>() {
    override fun getUsers(results: Int): Flow<ApiResult<List<User>>> = flow {
        val response = provider.getResults(results)
        handleResult(response, mapper)
    }
}