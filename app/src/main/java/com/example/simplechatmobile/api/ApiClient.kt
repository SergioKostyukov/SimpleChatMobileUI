package com.example.simplechatmobile.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8000/"

    fun getClient(token: String? = null): Retrofit {
        val client = OkHttpClient.Builder().apply {
            token?.let {
                addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Token $it")
                        .build()
                    chain.proceed(request)
                }
            }
        }.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}