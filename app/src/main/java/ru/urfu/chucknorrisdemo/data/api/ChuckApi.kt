package ru.urfu.chucknorrisdemo.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.urfu.chucknorrisdemo.data.JokeResponse

interface ChuckApi {
    @GET("jokes/random")
    suspend fun getRandomJoke(@Query("category") category: String?): JokeResponse

    @GET("jokes/categories")
    suspend fun getCategories(): List<String>
}

