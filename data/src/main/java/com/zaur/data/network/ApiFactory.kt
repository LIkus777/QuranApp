package com.zaur.data.network

import com.zaur.data.network.ApiConstants.BASE_URL_ALQURAN_CLOUD
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
* @author Zaur
* @since 2025-05-12
*/

object ApiFactory {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_ALQURAN_CLOUD)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}