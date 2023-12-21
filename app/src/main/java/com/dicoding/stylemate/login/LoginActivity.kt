package com.dicoding.stylemate.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.dicoding.stylemate.MainActivity
import com.dicoding.stylemate.R
import com.dicoding.stylemate.register.SignUpActivity
import com.dicoding.stylemate.data.DataPreferences
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val dataPreferences = DataPreferences(this)

        val btnLogin: Button = findViewById(R.id.btn_login)
        val tvRegister: TextView = findViewById(R.id.tv_register)

        val email: TextInputEditText = findViewById(R.id.edt_email)
        val password: TextInputEditText = findViewById(R.id.edt_password)

        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        editTextEmail = findViewById(R.id.edt_email)
        editTextPassword = findViewById(R.id.edt_password)


        loginViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[LoginViewModel::class.java]


        loginViewModel.isSukses.observe(this){
                progressBar.visibility = View.INVISIBLE
            if(it){
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
            dataPreferences.setEmailPass(email.text.toString(), password.text.toString())
            loginViewModel.login(email.text.toString(), password.text.toString())
            progressBar.visibility = View.VISIBLE

        }

        tvRegister.setOnClickListener {
            // Pindah ke halaman SignUpActivity ketika tombol "SignUp" ditekan
            navigateToSignUp()
        }

        editTextEmail.addTextChangedListener { validateEmail() }
        editTextPassword.addTextChangedListener { validatePassword() }
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

    private fun validateEmail() {
        val email = editTextEmail.text.toString().trim()

        if (email.isEmpty()) {
            editTextEmail.error = "Email tidak boleh kosong"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = "Masukkan alamat email yang valid"
        } else {
            editTextEmail.error = null  // Menghapus pesan kesalahan jika email valid
        }
    }

    private fun validatePassword() {
        val password = editTextPassword.text.toString().trim()

        if (password.length < 8) {
            editTextPassword.error = "Password harus memiliki setidaknya 8 karakter"
        } else {
            editTextPassword.error = null  // Menghapus pesan kesalahan jika password valid
        }
    }
}