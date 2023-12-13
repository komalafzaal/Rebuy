package com.example.rebuy.ViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rebuy.Model.Repository.ProductRepositoryImp
import com.example.rebuy.Model.data.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductRepositoryImp) : ViewModel(){

    val productsLiveData: LiveData<List<Product>> = repository.productsLiveData
    fun getAllProductsFromFirebase() {
        repository.getAllProductsFromFirebase()
    }

}