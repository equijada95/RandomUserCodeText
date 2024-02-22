package com.equijada95.domain.module

import com.equijada95.data.model.RandomUserResults
import com.equijada95.domain.mapper.BaseMapper
import com.equijada95.domain.mapper.user.UserMapper
import com.equijada95.domain.model.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserMapperModule {

    @Provides
    @Singleton
    fun providerRandomUserRepository(userMapper: UserMapper): BaseMapper<RandomUserResults, List<User>> = userMapper

}