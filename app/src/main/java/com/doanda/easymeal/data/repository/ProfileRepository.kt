package com.doanda.easymeal.data.repository

import com.doanda.easymeal.data.source.local.UserPreferences
import com.doanda.easymeal.data.source.remote.ApiService

class ProfileRepository (
    private val userPreferences: UserPreferences,
    private val apiService: ApiService,
) {


}