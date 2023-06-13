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
            val url = "https://backend-js-dot-capstone-project677.et.r.appspot.com/"
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}