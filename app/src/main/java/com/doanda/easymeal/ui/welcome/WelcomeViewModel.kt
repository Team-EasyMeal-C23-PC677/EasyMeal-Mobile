package com.doanda.easymeal.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doanda.easymeal.data.repository.UserRepository
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    fun getUser() = userRepository.getUser()

    fun getFirstTimeStatus() = userRepository.getFirstTimeStatus()

    fun setFirstTimeStatus(firstTimeStatus: Boolean) {
        viewModelScope.launch {
            userRepository.setFirstTimeStatus(firstTimeStatus)
        }
    }
}
