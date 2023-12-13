package com.example.rebuy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rebuy.databinding.ActivityAccountBinding
import com.example.rebuy.databinding.ActivityLoginBinding

class Account : AppCompatActivity() {
    private lateinit var binding : ActivityAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater) // Initialize binding

        setContentView(binding.root)

    }
}