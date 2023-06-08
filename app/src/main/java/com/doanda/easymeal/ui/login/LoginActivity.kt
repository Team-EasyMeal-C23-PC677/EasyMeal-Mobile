package com.doanda.easymeal.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.doanda.easymeal.MainActivity
import com.doanda.easymeal.R
import com.doanda.easymeal.data.source.model.UserEntity
import com.doanda.easymeal.databinding.ActivityLoginBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.register.RegisterActivity
import com.doanda.easymeal.ui.welcome.WelcomeActivity
import com.doanda.easymeal.utils.Result

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LoginViewModel>{ ViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupView()
    }

    private fun setupView() {
        viewModel.getFirstTimeStatus().observe(this) { firstTimeStatus ->
            if (firstTimeStatus == true) {
                goToWelcome()
            }
        }

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

        viewModel.login(email, password).observe(this) { result ->
            when(result) {
                is Result.Success -> {
                    showLoading(false)
                    val userData = result.data.user
                    if (userData != null) {
                        val user = UserEntity(
                            userId = userData.userId ?: 1,
                            userName = userData.userName ?: "null",
                            userEmail = userData.userEmail ?: "null",
                            userPassword = userData.userPassword ?: "null",
                            isLogin = true,
                            isFirstTime = false
                        )
                        viewModel.saveUser(user)

                        Toast.makeText(this, getString(R.string.response_login_success), Toast.LENGTH_SHORT).show()

                        goToMain()
                    }
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

    private fun goToRegister() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToWelcome() {
        val intentToWelcome = Intent(this@LoginActivity, WelcomeActivity::class.java)
        intentToWelcome.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentToWelcome)
        finish()
    }

    private fun goToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}