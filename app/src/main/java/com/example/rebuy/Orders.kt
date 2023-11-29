package com.example.rebuy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Orders : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        myOrderCards()
    }

    private fun myOrderCards()
    {
        val data = listOf(
            Product("Apple AirPods Pro", "21 Jan 2021","Company A", 899.0, "1K"),
            Product("Apple AirPods Pro", "21 Jan 2021","Company A", 899.0, "1K"),
            Product("Apple AirPods Pro", "21 Jan 2021","Company A", 899.0, "1K"),
            Product("Apple AirPods Pro", "21 Jan 2021","Company A", 899.0, "1K"),
            Product("Apple AirPods Pro", "21 Jan 2021","Company A", 899.0, "1K"),

            )

        // Initialize RecyclerView in Activity/Fragment
        val verticalRecyclerView: RecyclerView = findViewById(R.id.my_order_recycler_view)
        val verticalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.layoutManager = verticalLayoutManager

        val myOrderAdapter = MyOrderAdapter(data) // Pass your data to the adapter
        verticalRecyclerView.adapter = myOrderAdapter

    }
}