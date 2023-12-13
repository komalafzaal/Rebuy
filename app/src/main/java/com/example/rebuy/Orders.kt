package com.example.rebuy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rebuy.Adapters.MyOrderAdapter
import com.example.rebuy.Model.data.Product

class Orders : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        myOrderCards()
    }

    private fun myOrderCards()
    {
        val data = listOf(
            Product("Apple AirPods Pro", "Electronics",33.3,  "lahore", "this is A", "url"),

            )

        // Initialize RecyclerView in Activity/Fragment
        val verticalRecyclerView: RecyclerView = findViewById(R.id.my_order_recycler_view)
        val verticalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        verticalRecyclerView.layoutManager = verticalLayoutManager

        val myOrderAdapter = MyOrderAdapter(data) // Pass your data to the adapter
        verticalRecyclerView.adapter = myOrderAdapter

    }
}