package ru.urfu.chucknorrisdemo.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.urfu.chucknorrisdemo.core.networkUtils.NetworkManager
import ru.urfu.chucknorrisdemo.data.api.ChuckApi
import ru.urfu.chucknorrisdemo.data.repository.NetworkConnectivityObserver
import ru.urfu.chucknorrisdemo.domain.repository.INetworkConnectivityObserver

private const val BASE_URL = "https://api.chucknorris.io/"

val networkModule = module {
    factory { provideRetrofit(get()) }
    single { provideNetworkApi(get()) }
    single<INetworkConnectivityObserver> {
        NetworkConnectivityObserver(androidContext())
    }
    single { NetworkManager(get()) }
}

fun provideRetrofit(context: Context): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder().apply {
                addInterceptor {
                    Interceptor { chain ->
                        val request: Request = chain.request()
                        val url: HttpUrl = request.url.newBuilder().build()
                        chain.proceed(request.newBuilder().url(url).build())
                    }.intercept(it)
                }
                addInterceptor(ChuckerInterceptor(context))
            }.build()
        ).build()

}

fun provideNetworkApi(retrofit: Retrofit): ChuckApi = retrofit.create(ChuckApi::class.java)