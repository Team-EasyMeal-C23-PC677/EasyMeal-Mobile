package com.doanda.easymeal.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.doanda.easymeal.data.response.auth.AuthResponse
import com.doanda.easymeal.data.source.local.UserPreferences
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.utils.Result

class UserRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    fun register(
        name: String,
        email: String,
        password: String,
    ) : LiveData<Result<AuthResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(
        email: String,
        password: String,
    ) : LiveData<Result<AuthResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }




}
