package com.example.rebuy.Model.Repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rebuy.Model.data.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(val auth: FirebaseAuth, val db: FirebaseFirestore,
    val storage : StorageReference
) : ProductRepository {
    val productUploadResult = MutableLiveData<Boolean>()
    val productsResult = MutableLiveData<List<Product>>()
    val errorResult = MutableLiveData<Exception>()
    val productsLiveData = MutableLiveData<List<Product>>()

    override fun uploadImage(imageUri: Uri): Task<Uri> {
        val storageRef = storage.child("product_images/${System.currentTimeMillis()}.jpg")
        return storageRef.putFile(imageUri).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            storageRef.downloadUrl
        }
    }

    override fun saveProductToFirestore(product: Product) {
        db.collection("products")
            .add(product)
            .addOnSuccessListener { documentReference ->
                productUploadResult.postValue(true)
            }
            .addOnFailureListener { e ->
                productUploadResult.postValue(false)
            }
    }

    override fun getAllProductsFromFirebase(): LiveData<List<Product>> {

        db.collection("products")
            .get()
            .addOnSuccessListener { querySnapshot ->
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
                productsLiveData.postValue(productsList)
            }
            .addOnFailureListener { e ->
                // Handle failure, if needed
            }

        return productsLiveData
    }
}