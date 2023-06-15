package com.doanda.easymeal.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.login.User
import com.doanda.easymeal.data.source.model.UserEntity
import com.doanda.easymeal.databinding.ActivityLoginBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.main.MainActivity
import com.doanda.easymeal.ui.register.RegisterActivity
import com.doanda.easymeal.ui.welcome.WelcomeActivity
import com.doanda.easymeal.utils.Result
import observeOnce

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LoginViewModel>{ ViewModelFactory.getInstance(this) }

    private var pantryLoaded: Boolean = false
    private var favoriteLoaded: Boolean = false
    private var shoppingLoaded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupData()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.getFirstTimeStatus().removeObservers(this)
    }

    private fun setupData() {
        viewModel.getFirstTimeStatus().observeOnce(this) { firstTimeStatus ->
            if (firstTimeStatus == true) {
                goToWelcome()
            } else {
                setupView()
            }
        }
    }

    private fun setupView() {
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnToRegister.setOnClickListener {
            goToRegister()
        }
    }

    private fun login() {
        val email = binding.etLoginEmail.text.toString()
        val password = binding.etLoginPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.cannot_be_empty), Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.login(email, password).observe(this) { result ->
            when(result) {
                is Result.Success -> {
                    showLoading(false)
                    val userData = result.data.user
//                        Toast.makeText(this, getString(R.string.response_login_success), Toast.LENGTH_SHORT).show()
                    loadUserData(userData)
                }
                is Result.Error -> {
                    showLoading(false)
                    Log.e(TAG, result.error)
                    val message = if ("401" in result.error) getString(R.string.response_login_credentials_incorrect) else getString(
                                            R.string.response_login_failed)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> showLoading(true)
            }
        }
    }

    private fun loadUserData(userData: User) {
        val userId = userData.userId
        val user = UserEntity(
            userId = userData.userId,
            userEmail = userData.userEmail,
        )
        viewModel.saveUser(user)
        viewModel.setLoginStatus(true)
        viewModel.setUserName(userData.userName)

        viewModel.getPantryIngredients(userId).observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
//                    Toast.makeText(this, "Pantry Loaded", Toast.LENGTH_SHORT).show()
                    pantryLoaded = true
                    proceed(userData)
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    setAllChecks(false)
                    val message = "Failed to load Pantry"
                    Log.e(TAG, result.error + message)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getFavoriteRecipes(userId).observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
//                    Toast.makeText(this, "Favorite Loaded", Toast.LENGTH_SHORT).show()
                    favoriteLoaded = true
                    proceed(userData)
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    setAllChecks(false)
                    val message = "Failed to load favorite"
                    Log.e(TAG, result.error + message)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getShoppingList(userId).observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
//                    Toast.makeText(this, "Shopping List Loaded", Toast.LENGTH_SHORT).show()
                    shoppingLoaded = true
                    proceed(userData)
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    setAllChecks(false)
                    val message = "Failed to load Shopping List"
                    Log.e(TAG, result.error + message)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setAllChecks(isLoaded: Boolean) {
        pantryLoaded = isLoaded
        favoriteLoaded = isLoaded
        shoppingLoaded = isLoaded
    }

    private fun proceed(userData: User) {
        if (pantryLoaded && favoriteLoaded && shoppingLoaded) {
            pantryLoaded = false
            favoriteLoaded = false
            shoppingLoaded = false
            Toast.makeText(this, getString(R.string.response_login_success), Toast.LENGTH_SHORT).show()
            goToMain()
        }
    }

    private fun goToRegister() {
//        Toast.makeText(this, "Login -> Register", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        finish()
        startActivity(intent)
    }

    private fun goToWelcome() {
//        Toast.makeText(this, "Login -> Welcome", Toast.LENGTH_SHORT).show()
        val intentToWelcome = Intent(this@LoginActivity, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        finish()
        startActivity(intentToWelcome)
    }

    private fun goToMain() {
//        Toast.makeText(this, "Login -> Main", Toast.LENGTH_SHORT).show()
        val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
        intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        finish()
        startActivity(intentToMain)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}


