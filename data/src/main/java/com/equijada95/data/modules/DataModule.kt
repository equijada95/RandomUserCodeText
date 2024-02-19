package com.equijada95.heroapp.data.modules

import android.app.Application
import com.equijada95.data.provider.AppProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = "https://randomuser.me/api/".toHttpUrl()

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: HttpUrl) =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

    @Singleton
    @Provides
    fun providerHeroProvider(retrofit: Retrofit): AppProvider = retrofit.create(AppProvider::class.java)
}