package com.dicoding.stylemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.dicoding.stylemate.data.DataPreferences
import com.dicoding.stylemate.login.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val dataPreferences = DataPreferences(this)

        if(dataPreferences.getEmail() != ""){
            navigateToHome()
            finish()
        }

        val btnWelcome: Button = findViewById(R.id.button)

        btnWelcome.setOnClickListener {
            navigateToWelcome()
        }
    }

    private fun navigateToWelcome() {
        // Pindah ke halaman Welcome
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
    }
    private fun navigateToHome() {
        // Pindah ke halaman Welcome
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}