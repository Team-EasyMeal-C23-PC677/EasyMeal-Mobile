package com.doanda.easymeal.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.doanda.easymeal.data.source.model.UserEntity
import com.doanda.easymeal.databinding.ActivitySettingBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.login.LoginActivity
import com.doanda.easymeal.utils.Result
import observeOnce

class SettingActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SettingViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupData()
    }


    private fun setupData() {
        viewModel.getUser().observeOnce(this) { user ->
            if (user.userId != -1) {
                setupView(user)
                setupAction(user)
            }
        }
        viewModel.getUserName().observe(this) { name ->
            binding.tvUserName.text = name
            binding.etSettingName.setText(name)
        }
    }

    private fun setupView(user: UserEntity) {
        binding.tvUserEmail.text = user.userEmail
    }

    private fun setupAction(user: UserEntity) {
        binding.btnLogout.setOnClickListener {
            logout()
        }
        binding.btnUpdate.setOnClickListener {
            val nameInput = binding.etSettingName.text.toString()
            if (nameInput.isNotEmpty()) {
                viewModel.updateName(user.userId, nameInput).observe(this) { result ->
                    when (result) {
                        is Result.Success -> {
                            showLoading(false)
                            viewModel.setUserName(nameInput)
                            Toast.makeText(this, "Name changed successfully!", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Loading -> showLoading(true)
                        is Result.Error -> {
                            showLoading(false)
                            Log.e(TAG, "Update name ${result.error}")
                            Toast.makeText(this, "Failed to change name", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToLogin() {
        val intentToLogin = Intent(this@SettingActivity, LoginActivity::class.java)
        intentToLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentToLogin)
    }



    private fun logout() {
        viewModel.logout()
        viewModel.clearPantry()
        viewModel.clearFavorite()
        viewModel.clearShoppingList()
        goToLogin()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "SettingActivity"
    }
}