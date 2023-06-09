package com.doanda.easymeal.data.source.remote

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = 
                if (BuildConfig.DEBUG) 
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                else
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val dummyApiUrl = "https://easymeal-api.google.com/" // TODO use BuildConfig.API_URL
            val retrofit = Retrofit.Builder()
                .baseUrl(dummyApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}