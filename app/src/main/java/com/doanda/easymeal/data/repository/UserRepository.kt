package com.doanda.easymeal.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.login.LoginResponse
import com.doanda.easymeal.data.source.local.UserPreferences
import com.doanda.easymeal.data.source.model.UserEntity
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.utils.Result

class UserRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
) {
    fun register(
        name: String,
        email: String,
        password: String,
    ) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            Log.d(TAG, "Success register")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(
        email: String,
        password: String,
    ) : LiveData<Result<LoginResponse>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            Log.d(TAG, "Success login")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }


    fun updateName(userId: Int, userName: String) : LiveData<Result<GeneralResponse>>
    = liveData {
        try {
            val response = apiService.updateName(userId, userName)
            Log.d(TAG, "Success updateName")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun logout() = userPreferences.logout()

    fun getUser(): LiveData<UserEntity> {
        return userPreferences.getUser().asLiveData()
    }

    suspend fun saveUser(user: UserEntity) {
        userPreferences.saveUser(user)
    }

    fun getLoginStatus(): LiveData<Boolean> {
        return userPreferences.getLoginStatus().asLiveData()
    }

    suspend fun setLoginStatus(isLogin: Boolean) {
        userPreferences.setLoginStatus(isLogin)
    }

    fun getFirstTimeStatus(): LiveData<Boolean> {
        return userPreferences.getFirstTimeStatus().asLiveData()
    }

    suspend fun setFirstTimeStatus(firstTimeStatus: Boolean) {
        userPreferences.setFirstTimeStatus(firstTimeStatus)
    }

    fun getUserName() : LiveData<String> {
        return userPreferences.getUserName().asLiveData()
    }

    suspend fun setUserName(name: String) {
        userPreferences.setUserName(name)
    }

    companion object {
        private const val TAG = "UserRepositoryLoggg"

        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences,
        ) : UserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(apiService, userPreferences)
            }.also { INSTANCE = it }
    }
}
