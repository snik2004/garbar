package com.tinkofflab.garbar.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

const val TOKEN = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"

object ApiProvider {

    private val json = Json { ignoreUnknownKeys = true }

    fun provideRetrofit(
        authInterceptor: Interceptor,
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        val rxJavaCallAdapterFactory = RxJava3CallAdapterFactory.create()

        val contentType: MediaType? = "application/json".toMediaTypeOrNull()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType!!))
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    }

    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()
            .header("x-api-key", TOKEN)
        Timber.i("x-api-key: ${TOKEN}")
        val request = builder.build()
        chain.proceed(request)
    }
}