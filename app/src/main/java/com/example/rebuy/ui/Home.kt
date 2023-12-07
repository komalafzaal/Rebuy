package com.example.rebuy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rebuy.Adapters.ProductAdapter
import com.example.rebuy.Model.data.Product
import com.example.rebuy.R
import com.example.rebuy.SideBarActivity
import com.example.rebuy.databinding.ActivityHomeBinding
import com.google.firebase.firestore.FirebaseFirestore

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater) // Initialize binding

        setContentView(binding.root)
        readDataFromFirebase()


        val receivedIntent = intent
        val userName = receivedIntent.getStringExtra("USERNAME")
        val firstName = userName?.split(" ")?.firstOrNull() ?: ""
        binding.userId.text = "Hello $firstName"


        binding.hamburgIcon.setOnClickListener {
            val intent = Intent(this@Home, SideBarActivity::class.java)
            startActivity(intent)
        }


        binding.bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> {
                    readDataFromFirebase()
                    true
                }
                R.id.explore_menu -> {
                    Toast.makeText(this, "explore menu", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.camera_menu -> {
                    startActivity(Intent(this, PostProduct::class.java))
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

//    val data = listOf(
//        Product("Apple AirPods Pro", "Electronics",33.3,  "lahore", "this is A", "url"),
//
//        )

    private fun readDataFromFirebase() {
        val db = FirebaseFirestore.getInstance()
        val productsCollection = db.collection("products")

        productsCollection.get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val productsList = mutableListOf<Product>()

                    for (document in querySnapshot) {
                        val name = document.getString("name").toString()
                        val productType = document.getString("type").toString()
                        val productPrice = document.getDouble("price") ?: 0.0
                        val productLocation = document.getString("location").toString()
                        val productDetail = document.getString("detail").toString()
                        val imageUrl = document.getString("image_url").toString()

                        val product = Product(
                            name,
                            productType,
                            productPrice,
                            productLocation,
                            productDetail,
                            imageUrl
                        )
                        productsList.add(product)
                    }
                    newArrivals(productsList)
                    recentlyViewed(productsList)
                } else {
                    Log.d("Firestore", "No products found")
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error reading document", e)
            }
    }


    private fun newArrivals(productData: List<Product> )
    {

        // Initialize RecyclerView in Activity/Fragment
        val horizontalRecyclerView = binding.newArrivalsRecyclerView
        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.layoutManager = horizontalLayoutManager

        val newArrivalAdapter = ProductAdapter(productData)
        horizontalRecyclerView.adapter = newArrivalAdapter


    }
    private fun recentlyViewed(productData: List<Product> )
    {
        // Initialize RecyclerView in Activity/Fragment
        val horizontalRecyclerView1 =   binding.recentlyViewedRecyclerView
        val horizontalLayoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView1.layoutManager = horizontalLayoutManager1

        val recentlyViewAdapter = ProductAdapter(productData)
        horizontalRecyclerView1.adapter = recentlyViewAdapter

    }

}