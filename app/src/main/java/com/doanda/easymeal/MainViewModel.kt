package com.doanda.easymeal

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.UserRepository

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUser() = userRepository.getUser()
}
