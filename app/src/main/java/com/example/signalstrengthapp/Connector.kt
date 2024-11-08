package com.example.signalstrengthapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Connector {
    private const val BASE_URL = "http://10.151.5.144:3000/"

    private val retrofit by lazy {
        // Set up logging for Retrofit
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)  // Add the logging interceptor to OkHttpClient
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)  // Set the client with logging enabled
            .build()
    }

    val api: SignalStrengthService by lazy {
        retrofit.create(SignalStrengthService::class.java)
    }
}
