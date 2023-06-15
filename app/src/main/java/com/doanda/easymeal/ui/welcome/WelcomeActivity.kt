package com.doanda.easymeal.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.doanda.easymeal.databinding.ActivityWelcomeBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.login.LoginActivity
import com.doanda.easymeal.ui.register.RegisterActivity
import com.doanda.easymeal.utils.Result

class WelcomeActivity : AppCompatActivity() {

    private val binding: ActivityWelcomeBinding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<WelcomeViewModel> { ViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupData()
    }

    private fun setupData() {
        viewModel.setFirstTimeStatus(false)
        viewModel.getAllIngredients().observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                    if (result.data.isNotEmpty())
//                        Toast.makeText(this, "Ingredients Loaded", Toast.LENGTH_SHORT).show()
                    setupView()
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    val message = "Failed to load ingredients"
                    Log.e(TAG, result.error + message)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView() {
        binding.btnToRegister.setOnClickListener {
            goToRegister()
        }
        binding.btnToLogin.setOnClickListener {
            goToLogin()
        }
    }

    private fun goToLogin() {
        val intentToLogin = Intent(this@WelcomeActivity, LoginActivity::class.java)
        intentToLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//        Toast.makeText(this, "Welcome -> Login", Toast.LENGTH_SHORT).show()
        startActivity(intentToLogin)
        finish()
    }

    private fun goToRegister() {
        val intentToRegister = Intent(this@WelcomeActivity, RegisterActivity::class.java)
        intentToRegister.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//        Toast.makeText(this, "Welcome -> Register", Toast.LENGTH_SHORT).show()
        startActivity(intentToRegister)
        finish()
    }

    companion object {
        private const val TAG = "WelcomeActivityLog"
    }
}