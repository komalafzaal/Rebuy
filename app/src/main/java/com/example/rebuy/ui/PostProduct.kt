package com.example.rebuy.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rebuy.Model.data.Product
import com.example.rebuy.R
import com.example.rebuy.ViewModels.ProductViewModel
import com.example.rebuy.ViewModels.SignupViewModel
import com.example.rebuy.databinding.ActivityPostProductBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class PostProduct : AppCompatActivity() {
    private lateinit var binding: ActivityPostProductBinding
    private var selectedImageUri: Uri? = null
    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostProductBinding.inflate(layoutInflater) // Initialize binding

        setContentView(binding.root)

        val languages = resources.getStringArray(R.array.Product_Type)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown, languages)
        val autocompleteTV = binding.autoCompleteTextView
        autocompleteTV.setAdapter(arrayAdapter)

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        binding.backBtn.setOnClickListener{
            finish()
        }

        binding.uploadImage.setOnClickListener{
            ImagePicker.with(this)
                .galleryOnly()
                .crop()
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()

        }

        binding.postBtn.setOnClickListener {
            val productType = binding.autoCompleteTextView.text.toString()
            val productName = binding.nameInput.text.toString()
            val productPrice = binding.priceInput.text.toString().toDouble()
            val productLocation = binding.locationInput.text.toString()
            val productDetail = binding.detailInput.text.toString()


            val filePath = selectedImageUri?.path
            if (filePath != null) {
                viewModel.uploadImage(selectedImageUri!!)
                    .addOnSuccessListener { imageUrl ->
                        val product = Product(
                            productName,
                            productType,
                            productPrice,
                            productLocation,
                            productDetail,
                            imageUrl.toString()
                        )
                        viewModel.saveProductToFirestore(product)
                    }
                    .addOnFailureListener { exception ->
                        Log.e("ImageUpload", "Image upload failed: ${exception.message}")
                    }
            } else {
                Log.d("ImageUpload", "No image selected")
            }
        }


        viewModel.productUploadResult.observe(this, Observer { isSuccessful ->
            if (isSuccessful) {
                Toast.makeText(this, "Product Uploaded Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to upload product", Toast.LENGTH_SHORT).show()
            }
        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            selectedImageUri = data?.data
            // Handle displaying the selected image in the UI if required
            binding.uploadImage.setImageURI(selectedImageUri)
            Log.d("Image", "Image uploaded successfully")
        }
    }

}