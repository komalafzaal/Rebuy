package com.example.rebuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> {
                    startActivity(Intent(this, Home::class.java))
                    true
                }
                R.id.explore_menu -> {
                    startActivity(Intent(this, Home::class.java))
                    true
                }
                R.id.camera_menu -> {
                    openCamera()
                    true
                }
                R.id.favourites_menu -> {
                    startActivity(Intent(this, Home::class.java))
                    true
                }
                R.id.chat_menu -> {
                    startActivity(Intent(this, Home::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            startActivity(cameraIntent)
        } else {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show()
        }
    }
}