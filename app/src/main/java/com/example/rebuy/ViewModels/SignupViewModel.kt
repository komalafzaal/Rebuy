package com.example.rebuy.ViewModels

import androidx.lifecycle.ViewModel
import com.example.rebuy.Model.Repository.AuthRepositoryImp
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val repository: AuthRepositoryImp) : ViewModel() {

    val signupResultLiveData = repository.signupResultLiveData
    val userLiveData = repository.userLiveData
    fun signupWithEmailAndPassword(email: String, password: String, name: String) =
        repository.signupWithEmailAndPassword(email, password, name)

    fun signupWithGoogle(account: GoogleSignInAccount) =
        repository.signupWithGoogle(account)
}
