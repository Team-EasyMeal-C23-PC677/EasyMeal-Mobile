package com.doanda.easymeal.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.doanda.easymeal.R
import com.doanda.easymeal.databinding.ActivityRegisterBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.login.LoginActivity
import com.doanda.easymeal.utils.Result

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RegisterViewModel>{ ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupView()
    }

    private fun setupView() {
        binding.btnRegister.setOnClickListener {
            register()
        }
        binding.btnToLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun register() {
        val name = binding.etRegisterName.text.toString()
        val email = binding.etRegisterEmail.text.toString()
        val password = binding.etRegisterPassword.text.toString()
        val confirmPassword = binding.etRegisterConfirm.text.toString()
        if (password != confirmPassword) {
            Toast.makeText(this, getString(R.string.response_register_confirm_fail), Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.register(name, email, password).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                        Toast.makeText(this, getString(R.string.response_register_success), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Log.e(TAG, result.error)
                        Toast.makeText(this, getString(R.string.response_register_failed), Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> showLoading(true)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}