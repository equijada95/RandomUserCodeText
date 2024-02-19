package com.equijada95.data.provider

import com.equijada95.data.model.RandomUserModel
import retrofit2.Response
import retrofit2.http.GET

interface AppProvider {

    @GET("?results=20")
    suspend fun getAll(): Response<List<RandomUserModel>>

}