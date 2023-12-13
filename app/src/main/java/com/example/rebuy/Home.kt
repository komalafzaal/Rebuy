package com.example.rebuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        newArrivals()
        recentlyViewed()

        val sideBar : ImageButton  = findViewById(R.id.hamburg_icon)

        sideBar.setOnClickListener {
            val intent = Intent(this@Home, SideBarActivity::class.java)
            startActivity(intent)
        }

        var bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> {
                    newArrivals()
                    true
                }
                R.id.explore_menu -> {
                    Toast.makeText(this, "explore menu", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.camera_menu -> {
                    openCamera()
                    true
                }
                R.id.favourites_menu -> {
                    Toast.makeText(this, "favorites manu", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.chat_menu -> {
                    Toast.makeText(this, "chat menu", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    val data = listOf(
        Product("Apple AirPods Pro", "21 Jan 2021","Company A", 899.0, "1K"),
        Product("Apple AirPods Pro", "21 Jan 2021","Company A", 899.0, "1K"),
        Product("Apple AirPods Pro", "21 Jan 2021","Company A", 899.0, "1K"),
        Product("Apple AirPods Pro", "21 Jan 2021","Company A", 899.0, "1K"),

        )
    private fun newArrivals()
    {

        // Initialize RecyclerView in Activity/Fragment
        val horizontalRecyclerView: RecyclerView = findViewById(R.id.new_arrivals_recycler_view)
        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.layoutManager = horizontalLayoutManager

        val newArrivalAdapter = ProductAdapter(data) // Pass your data to the adapter
        horizontalRecyclerView.adapter = newArrivalAdapter


    } private fun recentlyViewed()
    {
        // Initialize RecyclerView in Activity/Fragment
        val horizontalRecyclerView1: RecyclerView = findViewById(R.id.recently_viewed_recycler_view)
        val horizontalLayoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView1.layoutManager = horizontalLayoutManager1

        val recentlyViewAdapter = ProductAdapter(data) // Pass your data to the adapter
        horizontalRecyclerView1.adapter = recentlyViewAdapter

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