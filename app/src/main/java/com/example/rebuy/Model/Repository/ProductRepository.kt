package com.example.rebuy.Model.Repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.rebuy.Model.data.Product
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface ProductRepository {
    fun uploadImage(imageUri: Uri): Task<Uri>
    fun saveProductToFirestore(product: Product)
    fun getAllProductsFromFirebase(): LiveData<List<Product>>
}