package com.dicoding.stylemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Onboarding1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding1)

        val btnNext: ImageButton = findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            navigateToNextPage()
        }
    }

    private fun navigateToNextPage() {
        // Pindah ke halaman OnBoarding2Activity
        val intent = Intent(this, Onboarding2::class.java)
        startActivity(intent)
        finish()
    }
}