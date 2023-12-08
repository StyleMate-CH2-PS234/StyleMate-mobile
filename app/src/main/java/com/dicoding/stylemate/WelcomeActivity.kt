package com.dicoding.stylemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Pastikan ID yang Anda gunakan sesuai dengan yang ada di layout
        val btnLogin: Button = findViewById(R.id.btn_login)
        val btnSignUp: Button = findViewById(R.id.btn_register)

        btnLogin.setOnClickListener {
            navigateToLogin()
        }

        btnSignUp.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun navigateToLogin() {
        // Pindah ke halaman Login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSignUp() {
        // Pindah ke halaman SignUp
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}