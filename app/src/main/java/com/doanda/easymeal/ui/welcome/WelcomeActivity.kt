package com.doanda.easymeal.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.doanda.easymeal.MainActivity
import com.doanda.easymeal.databinding.ActivityWelcomeBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.login.LoginActivity
import com.doanda.easymeal.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private val binding: ActivityWelcomeBinding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<WelcomeViewModel> { ViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupData()
    }

    private fun setupData() {
        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                goToMain()
            } else {
                if (user.isFirstTime) {
                    viewModel.setFirstTimeStatus(false)
                    setupView()
                } else {
                    goToLogin()
                }
            }
        }
    }

    private fun goToMain() {
        val intentToMain = Intent(this@WelcomeActivity, MainActivity::class.java)
        intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentToMain)
        finish()
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
        startActivity(intentToLogin)
    }

    private fun goToRegister() {
        val intentToRegister = Intent(this@WelcomeActivity, RegisterActivity::class.java)
        startActivity(intentToRegister)
    }
}