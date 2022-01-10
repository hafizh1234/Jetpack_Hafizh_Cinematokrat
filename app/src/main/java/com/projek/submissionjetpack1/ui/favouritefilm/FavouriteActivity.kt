package com.projek.submissionjetpack1.ui.favouritefilm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.projek.submissionjetpack1.databinding.ActivityFavouriteBinding

class FavouriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewPager = FavouritePagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = viewPager
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f
    }
}