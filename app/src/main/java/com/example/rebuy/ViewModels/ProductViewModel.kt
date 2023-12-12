package com.example.rebuy.ViewModels
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rebuy.Model.Repository.ProductRepositoryImp
import com.example.rebuy.Model.data.Product
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepositoryImp) : ViewModel()
{
    val productUploadResult = repository.productUploadResult

    fun uploadImage(imageUri: Uri): Task<Uri> {
        return repository.uploadImage(imageUri)
    }
    fun saveProductToFirestore(product: Product)
    {
        repository.saveProductToFirestore(product)
    }
}