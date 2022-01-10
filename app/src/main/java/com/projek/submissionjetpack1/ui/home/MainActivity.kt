package com.projek.submissionjetpack1.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.projek.submissionjetpack1.R
import com.projek.submissionjetpack1.databinding.ActivityMainBinding
import com.projek.submissionjetpack1.ui.favouritefilm.FavouriteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var menu:Menu?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewPager = SectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = viewPager
        binding.tabs.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favourite_list, menu)
        this.menu = menu
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.list_fav) {
            val intent=Intent(this,FavouriteActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}