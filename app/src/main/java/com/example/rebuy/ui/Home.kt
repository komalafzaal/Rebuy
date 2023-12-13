package com.example.rebuy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rebuy.Adapters.ProductAdapter
import com.example.rebuy.Model.data.Product
import com.example.rebuy.R
import com.example.rebuy.SideBarActivity
import com.example.rebuy.ViewModels.HomeViewModel
import com.example.rebuy.ViewModels.LoginViewModel
import com.example.rebuy.databinding.ActivityHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater) // Initialize binding
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        fetchProducts()
        

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
                    fetchProducts()
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

    fun fetchProducts()
    {
        viewModel.getAllProductsFromFirebase()
        viewModel.productsLiveData.observe(this) { productsList ->
            newArrivals(productsList)
            recentlyViewed(productsList)
        }
    }

    private fun newArrivals(productsList: List<Product> )
    {

        // Initialize RecyclerView in Activity/Fragment
        val horizontalRecyclerView = binding.newArrivalsRecyclerView
        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.layoutManager = horizontalLayoutManager

        val newArrivalAdapter = ProductAdapter(productsList)
        horizontalRecyclerView.adapter = newArrivalAdapter


    }
    private fun recentlyViewed(productsList: List<Product> )
    {
        // Initialize RecyclerView in Activity/Fragment
        val horizontalRecyclerView1 =   binding.recentlyViewedRecyclerView
        val horizontalLayoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView1.layoutManager = horizontalLayoutManager1

        val recentlyViewAdapter = ProductAdapter(productsList)
        horizontalRecyclerView1.adapter = recentlyViewAdapter

    }
}
