package com.example.hical.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hical.R
import com.example.hical.dashboard.DashboardActivity
import com.example.hical.loginpage.LoginActivity
import com.example.hical.preference.UserPreference
import com.example.hical.preference.dataStore
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userPreference = UserPreference.getInstance(dataStore)

        lifecycleScope.launch {
            userPreference.isUserLoggedIn().collect { isLoggedIn ->
                if (isLoggedIn) {
                    // Pengguna sudah login, lanjutkan ke DashboardActivity
                    startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
                } else {
                    // Pengguna belum login, arahkan ke LoginActivity
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                }
                finish()
            }
        }
    }
}
