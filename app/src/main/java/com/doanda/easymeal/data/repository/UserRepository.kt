package com.doanda.easymeal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.login.LoginResponse
import com.doanda.easymeal.data.source.local.UserPreferences
import com.doanda.easymeal.data.source.model.UserEntity
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.data.source.remote.DummyApiService
import com.doanda.easymeal.utils.Result
import kotlinx.coroutines.flow.distinctUntilChanged

class UserRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
    private val dummyApiService: DummyApiService,
) {
    fun register(
        name: String,
        email: String,
        password: String,
    ) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.register(name, email, password)
            val response = dummyApiService.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
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
//            val response = apiService.login(email, password)
            val response = dummyApiService.login(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    fun updateName(userId: Int, userName: String) : LiveData<Result<GeneralResponse>>
    = liveData {
        try {
//            val response = apiService.updateName(userId, userName)
            val response = dummyApiService.updateName(userId, userName)
            emit(Result.Success(response))
        } catch (e: Exception) {
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
        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences,
            dummyApiService: DummyApiService,
        ) : UserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(apiService, userPreferences, dummyApiService)
            }.also { INSTANCE = it }
    }
}
