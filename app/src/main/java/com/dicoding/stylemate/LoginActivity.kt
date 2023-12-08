package com.dicoding.stylemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin: Button = findViewById(R.id.btn_login)
        val tvRegister: TextView = findViewById(R.id.tv_register)

        btnLogin.setOnClickListener {
            // Handle login logic here

            // Setelah login, pindah ke halaman berikutnya (contoh: HomeActivity)
            navigateToHome()
        }

        tvRegister.setOnClickListener {
            // Pindah ke halaman SignUpActivity ketika tombol "SignUp" ditekan
            navigateToSignUp()
        }
    }

    private fun navigateToHome() {
        // Pindah ke halaman HomeActivity atau halaman setelah login
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional: tutup activity ini agar tidak bisa dikembali
    }

    private fun navigateToSignUp() {
        // Pindah ke halaman SignUpActivity
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}