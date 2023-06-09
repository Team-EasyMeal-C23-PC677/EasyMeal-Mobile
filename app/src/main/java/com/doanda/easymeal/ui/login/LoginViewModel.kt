package com.doanda.easymeal.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doanda.easymeal.data.repository.UserRepository
import com.doanda.easymeal.data.source.model.UserEntity
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    fun login(email: String, password: String) =
        userRepository.login(email, password)

    fun saveUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.saveUser(user)
        }
    }

    fun getFirstTimeStatus() = userRepository.getFirstTimeStatus()
}
