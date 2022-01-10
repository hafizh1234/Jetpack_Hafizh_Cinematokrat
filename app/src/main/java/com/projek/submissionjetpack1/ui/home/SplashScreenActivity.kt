package com.projek.submissionjetpack1.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projek.submissionjetpack1.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val timeLoading=2000L
        supportActionBar?.hide()
        MainScope().launch {
            delay(timeLoading)
            val home = Intent(this@SplashScreenActivity,MainActivity::class.java)
            startActivity(home)
            finish()
        }

    }
}