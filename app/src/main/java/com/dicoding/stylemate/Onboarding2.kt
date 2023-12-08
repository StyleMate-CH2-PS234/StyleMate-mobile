package com.dicoding.stylemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Onboarding2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding2)

        val btnNext: ImageButton = findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            navigateToNextPage()
        }
    }

    private fun navigateToNextPage() {
        // Pindah ke halaman OnBoarding3Activity
        val intent = Intent(this, Onboarding3::class.java)
        startActivity(intent)
        finish()
    }
}