package com.doanda.easymeal.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.doanda.easymeal.data.source.model.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun getUser(): Flow<UserEntity> {
        return dataStore.data.map  { preferences ->
            UserEntity(
                preferences[USER_ID] ?: -1,
                preferences[USER_EMAIL] ?: "null",
            )
        }
    }

    suspend fun saveUser(user: UserEntity) {
        dataStore.edit {  preferences->
            preferences[USER_ID] = user.userId
            preferences[USER_EMAIL] = user.userEmail
        }
    }

    fun getUserName() : Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_NAME] ?: "null"
        }
    }

    suspend fun setUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }

    fun getLoginStatus() : Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_LOGIN] ?: false
        }
    }

    suspend fun setLoginStatus(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGIN] = isLogin
        }
    }

    fun getFirstTimeStatus() : Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_FIRST_TIME] ?: true
        }
    }

    suspend fun setFirstTimeStatus(firstTimeStatus: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_FIRST_TIME] = firstTimeStatus
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID)
            preferences.remove(USER_NAME)
            preferences.remove(USER_EMAIL)
            preferences.remove(IS_LOGIN)
        }
    }

    companion object {
        private val USER_ID = intPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val IS_LOGIN = booleanPreferencesKey("is_login")
        private val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")

        @Volatile
        private var INSTANCE: UserPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}