package com.example.rebuy

import SideBarAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rebuy.databinding.ActivityAccountBinding
import com.example.rebuy.databinding.ActivitySideBarBinding
import com.google.firebase.auth.FirebaseAuth

class SideBarActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySideBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySideBarBinding.inflate(layoutInflater) // Initialize binding
        setContentView(binding.root)
        sideBarCards()
        val user = FirebaseAuth.getInstance().currentUser


        binding.closeBtn.setOnClickListener {
            finish()
        }

        binding.signOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                navigateToLogin()
            }
        }
    }
    private fun navigateToLogin() {
        val intent = Intent(this, Login::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    private fun sideBarCards() {
        val data = listOf(
            SideBar("My Account", "Edit you details, account settings"),
            SideBar("My Orders", "View all your orders"),
            SideBar("My Listings", "View all your orders"),
            SideBar( "Liked Items", "See the products you have wishlisted"),

            )
        // Initialize RecyclerView in Activity/Fragment
        val verticalRecyclerView: RecyclerView = findViewById(R.id.side_bar_recycler_view)
        val verticalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.layoutManager = verticalLayoutManager

        val sideBarAdapter = SideBarAdapter(data, this) // Pass your data to the adapter
        verticalRecyclerView.adapter = sideBarAdapter
    }
}