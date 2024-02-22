package com.equijada95.domain.module

import com.equijada95.data.model.RandomUserResults
import com.equijada95.domain.mapper.BaseMapper
import com.equijada95.domain.mapper.user.UserMapper
import com.equijada95.domain.model.User
import com.equijada95.domain.repository.RandomUserRepository
import com.equijada95.domain.repository.RandomUserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun providesUserMapper(userMapper: UserMapper): BaseMapper<RandomUserResults, List<User>> = userMapper

    @Provides
    @Singleton
    fun providesRandomUserRepository(randomUserRepositoryImpl: RandomUserRepositoryImpl): RandomUserRepository = randomUserRepositoryImpl

}