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
import com.example.rebuy.databinding.ActivityHomeBinding
import com.example.rebuy.databinding.ActivitySignUpBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater) // Initialize binding

        setContentView(binding.root)
        newArrivals()
        recentlyViewed()

//        val receivedIntent = intent
//        val userName = receivedIntent.getStringExtra("USERNAME")
//        binding.userId.text = "Hello $userName"

        binding.hamburgIcon.setOnClickListener {
            val intent = Intent(this@Home, SideBarActivity::class.java)
            startActivity(intent)
        }


        binding.bottomNav.setOnNavigationItemSelectedListener { menuItem ->
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
        val horizontalRecyclerView = binding.newArrivalsRecyclerView
        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.layoutManager = horizontalLayoutManager

        val newArrivalAdapter = ProductAdapter(data)
        horizontalRecyclerView.adapter = newArrivalAdapter


    }
    private fun recentlyViewed()
    {
        // Initialize RecyclerView in Activity/Fragment
        val horizontalRecyclerView1 =   binding.recentlyViewedRecyclerView
        val horizontalLayoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView1.layoutManager = horizontalLayoutManager1

        val recentlyViewAdapter = ProductAdapter(data)
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