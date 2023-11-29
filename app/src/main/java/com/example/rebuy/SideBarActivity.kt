package com.example.rebuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SideBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_bar)
        sideBarCards()

        val close : ImageButton = findViewById(R.id.close_btn)

        close.setOnClickListener {
            finish()
        }
    }

    private fun sideBarCards()
    {
        val data = listOf(
            SideBar("@drawable/my_account_icon", "My Account","Edit you details, account settings"),
            SideBar("@drawable/my_order_icon", "My Orders","View all your orders"),
            SideBar("@drawable/my_listing_icon", "My Listings","View all your orders"),
            SideBar("@drawable/liked_items_icon", "Liked Items","See the products you have wishlisted"),

        )

        // Initialize RecyclerView in Activity/Fragment
        val verticalRecyclerView: RecyclerView = findViewById(R.id.side_bar_recycler_view)
        val verticalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.layoutManager = verticalLayoutManager

        val sideBarAdapter = SideBarAdapter(data) // Pass your data to the adapter
        verticalRecyclerView.adapter = sideBarAdapter




    }
}