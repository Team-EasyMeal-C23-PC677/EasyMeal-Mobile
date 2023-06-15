package com.doanda.easymeal.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.doanda.easymeal.R
import com.doanda.easymeal.databinding.ActivityMainBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.detection.DetectionActivity
import com.doanda.easymeal.ui.login.LoginActivity
import com.doanda.easymeal.ui.setting.SettingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import observeOnce

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>{ ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        hideSystemUI()

        setupData()
        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.getLoginStatus().removeObservers(this)
    }

    private fun setupData() {
        viewModel.getLoginStatus().observeOnce(this) { isLogin ->
            if (!isLogin) {
                goToLogin()
            }
        }
        viewModel.getUser().observe(this) { user ->
            if (user.userEmail != "null") {
                binding.tvUserEmail.text = user.userEmail
            }
        }
        viewModel.getUserName().observe(this) { name ->
            if (name != "null") {
                binding.tvUserName.text = name
            }
        }
    }

    private fun setupView() {
        val navView: BottomNavigationView = binding.navView
        navView.background = null
        navView.menu.getItem(2).isEnabled = false

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_recipe,
                R.id.navigation_pantry,
                R.id.navigation_favorite,
                R.id.navigation_shopping_list
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.fabCamera.setOnClickListener {
            goToDetection()
        }
        binding.ivUserIcon.setOnClickListener {
            goToSetting()
        }
    }

    private fun goToLogin() {
//        Toast.makeText(this, "Main -> Login", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        finish()
        startActivity(intent)
    }

    private fun goToSetting() {
//        Toast.makeText(this, "Main -> Setting", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@MainActivity, SettingActivity::class.java)
        startActivity(intent)
    }


    private fun goToDetection() {
        val intent = Intent(this@MainActivity, DetectionActivity::class.java)
        startActivity(intent)
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}