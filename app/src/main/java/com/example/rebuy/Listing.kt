package com.example.rebuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Listing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)
        myListingCards()

    }


    private fun myListingCards()
    {
        val data = listOf(
            Product("Apple AirPods Pro", "Electronics",33.3,  "lahore", "this is A", "url"),

            )

        val verticalRecyclerView: RecyclerView = findViewById(R.id.my_listing_recycler_view)
        val verticalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.layoutManager = verticalLayoutManager

        val myListingAdapter = MyListingAdapter(data) // Pass your data to the adapter
        verticalRecyclerView.adapter = myListingAdapter

    }
}