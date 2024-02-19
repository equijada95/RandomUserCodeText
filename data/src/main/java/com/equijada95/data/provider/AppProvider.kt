package com.equijada95.data.provider

import com.equijada95.data.model.RandomUserResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppProvider {

    @GET("?")
    suspend fun getResults(@Query("results") results: Int): Response<RandomUserResults>

}