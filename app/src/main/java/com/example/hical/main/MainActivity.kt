package com.example.hical.main


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hical.ViewModelFactory
import com.example.hical.databinding.ActivityMainBinding
import com.example.hical.loginpage.LoginActivity
import com.example.hical.welcome.WelcomeActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                setupAction()
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }
    private fun setupAction() {
        showLoading(true)
        lifecycleScope.launch {
            try {
            } catch (e: HttpException) {
                val errorMessage = e.message ?: "An error occurred on the server."
                if (e.code() == 401) {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    showToast("Login failed")
                    startActivity(intent)
                } else {
                    Log.e("MainViewModel", "Failed fetch story: $errorMessage")
                }
            } finally {
                showLoading(false)
                showToast("Welcome")
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
