package com.dicoding.stylemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Onboarding3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding3)

        val btnNext: ImageButton = findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            navigateToLandingPage()
        }
    }

    private fun navigateToLandingPage() {
        // Pindah ke halaman LandingPageActivity
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}