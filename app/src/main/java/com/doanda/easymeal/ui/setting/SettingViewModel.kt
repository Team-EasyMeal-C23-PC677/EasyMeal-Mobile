package com.doanda.easymeal.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doanda.easymeal.data.repository.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel( private val userRepository: UserRepository) : ViewModel() {

    fun getUser() = userRepository.getUser()

    fun updateName(userId: Int, userName : String) = userRepository.updateName(userId, userName)

    fun setUserName(name: String) {
        viewModelScope.launch {
            userRepository.setUserName(name)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

}
