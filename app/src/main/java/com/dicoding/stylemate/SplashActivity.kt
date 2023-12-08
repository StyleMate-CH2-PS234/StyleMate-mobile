package com.dicoding.stylemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val splash = Intent(this, Onboarding1::class.java)
            startActivity(splash)
            finish()
        }, SPLASH_DELAY)

        if (getSupportActionBar() != null) {
            supportActionBar?.hide()
        }
    }
}