package ru.urfu.chucknorrisdemo.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.urfu.chucknorrisdemo.data.api.ChuckApi
import kotlin.getValue

object RetrofitClient {
    val api: ChuckApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChuckApi::class.java)
    }
}