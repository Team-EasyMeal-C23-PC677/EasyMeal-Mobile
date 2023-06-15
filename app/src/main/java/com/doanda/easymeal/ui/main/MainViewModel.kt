package com.doanda.easymeal.ui.main

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.UserRepository

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    fun getUser() = userRepository.getUser()
    fun getLoginStatus() = userRepository.getLoginStatus()
    fun getUserName() = userRepository.getUserName()
}
