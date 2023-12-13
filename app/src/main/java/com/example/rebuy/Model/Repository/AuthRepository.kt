package com.example.rebuy.Model.Repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    fun signInWithGoogle(account: GoogleSignInAccount)
    fun signupWithEmailAndPassword(email: String, password: String, name:String)
    fun signupWithGoogle(account: GoogleSignInAccount)
}
