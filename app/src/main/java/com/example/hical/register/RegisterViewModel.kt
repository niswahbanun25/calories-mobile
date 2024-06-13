package com.example.hical.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hical.repository.RetrofitRepository
import com.example.hical.response.RegisterResponse

class RegisterViewModel (private val retrofitrepository: RetrofitRepository) : ViewModel() {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        try {
            val response = retrofitrepository.register(name, email, password)
            Log.d("RegisterProcess", "Proses registrasi berhasil: $response")
            return response
        } catch (e: Exception) {
            val errorMessage = e.message ?: "Terjadi kesalahan pada server."
            Log.e("RegisterProcess", "Proses registrasi gagal: $errorMessage")
            throw e
        }
    }
}