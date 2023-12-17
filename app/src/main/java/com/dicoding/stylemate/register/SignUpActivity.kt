package com.dicoding.stylemate.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.stylemate.R
import com.dicoding.stylemate.login.LoginActivity
import com.dicoding.stylemate.login.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpViewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signUpViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SignUpViewModel::class.java]


        val btnRegister: Button = findViewById(R.id.btnregis)
        val tvLogin: TextView = findViewById(R.id.tv_login)

        val name: TextInputEditText = findViewById(R.id.edt_username)
        val email: TextInputEditText = findViewById(R.id.edt_email)
        val password: TextInputEditText = findViewById(R.id.edt_password)


        signUpViewModel.isSukses.observe(this){
            if(it){
                Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                navigateToLogin()
            } else {
                Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            // Handle register logic here

            signUpViewModel.register(name.text.toString(), email.text.toString(), password.text.toString())
            // Setelah mendaftar, pindah ke halaman login
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
