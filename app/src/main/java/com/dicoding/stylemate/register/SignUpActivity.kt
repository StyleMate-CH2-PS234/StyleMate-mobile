package com.dicoding.stylemate.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.dicoding.stylemate.R
import com.dicoding.stylemate.login.LoginActivity
import com.dicoding.stylemate.login.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPasswordKonfirmasi: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signUpViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SignUpViewModel::class.java]


        val btnRegister: Button = findViewById(R.id.btnregis)
//        val tvLogin: TextView = findViewById(R.id.tv_login)

        val name: TextInputEditText = findViewById(R.id.edt_username)
        val email: TextInputEditText = findViewById(R.id.edt_email)
        val password: TextInputEditText = findViewById(R.id.edt_password)

        editTextEmail = findViewById(R.id.edt_email)
        editTextPassword = findViewById(R.id.edt_password)
        editTextPasswordKonfirmasi = findViewById(R.id.edt_konfirmasi_password)


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
            
            if(editTextPassword.text.toString()== editTextPasswordKonfirmasi.text.toString()){
                signUpViewModel.register(name.text.toString(), email.text.toString(), password.text.toString())
            } else {
                Toast.makeText(this, "Ketik ulang, password tidak sesuai", Toast.LENGTH_SHORT).show()
            }

            // Setelah mendaftar, pindah ke halaman login
        }

//        tvLogin.setOnClickListener {
//            // Pindah ke halaman LoginActivity ketika teks "Login" ditekan
//            navigateToLogin()
//        }
        editTextEmail.addTextChangedListener { validateEmail() }
        editTextPassword.addTextChangedListener { validatePassword() }
    }

    private fun navigateToLogin() {
        // Pindah ke halaman LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Optional: tutup activity ini agar tidak bisa dikembali
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
