package com.dicoding.stylemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnRegister: Button = findViewById(R.id.btnregis)
        val tvLogin: TextView = findViewById(R.id.tv_login)

        btnRegister.setOnClickListener {
            // Handle register logic here

            // Setelah mendaftar, pindah ke halaman login
            navigateToLogin()
        }

        tvLogin.setOnClickListener {
            // Pindah ke halaman LoginActivity ketika teks "Login" ditekan
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        // Pindah ke halaman LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Optional: tutup activity ini agar tidak bisa dikembali
    }
}
