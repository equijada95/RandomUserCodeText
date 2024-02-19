package com.equijada95.domain.module

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
    fun providerRandomUserRepository(randomUserRepositoryImpl: RandomUserRepositoryImpl): RandomUserRepositoryImpl = randomUserRepositoryImpl

}