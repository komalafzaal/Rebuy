package com.example.rebuy.ViewModels

import androidx.lifecycle.ViewModel
import com.example.rebuy.Model.Repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    val signInResultLiveData = repository.signInResultLiveData
    val userLiveData = repository.userLiveData
    fun signInWithEmailAndPassword(email: String, password: String) =
        repository.signInWithEmailAndPassword(email, password)

    fun signInWithGoogle(account : GoogleSignInAccount) =
        repository.signInWithGoogle(account)
}
