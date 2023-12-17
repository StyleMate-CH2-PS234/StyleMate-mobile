package com.dicoding.stylemate.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.stylemate.MainActivity
import com.dicoding.stylemate.R
import com.dicoding.stylemate.SignUpActivity
import com.dicoding.stylemate.data.DataPreferences
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val dataPreferences = DataPreferences(this)

        val btnLogin: Button = findViewById(R.id.btn_login)
        val tvRegister: TextView = findViewById(R.id.tv_register)

        val email: TextInputEditText = findViewById(R.id.edt_email)
        val password: TextInputEditText = findViewById(R.id.edt_password)

        val progressBar: ProgressBar = findViewById(R.id.progressBar)


        loginViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[LoginViewModel::class.java]


        loginViewModel.isSukses.observe(this){
            if(it){
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this, "Login Sukses", Toast.LENGTH_SHORT).show()
                navigateToHome()
            }else {
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.token.observe(this){
            dataPreferences.setToken(it!!)
        }

        btnLogin.setOnClickListener {
            // Handle login logic here
            loginViewModel.login(email.text.toString(), password.text.toString())
            progressBar.visibility = View.VISIBLE

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