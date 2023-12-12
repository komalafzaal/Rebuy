package com.example.rebuy.Model.Repository

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.rebuy.Model.data.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class AuthRepositoryImp @Inject constructor(val auth: FirebaseAuth, val db: FirebaseFirestore) : AuthRepository {
    val signInResultLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val signupResultLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val userLiveData: MutableLiveData<User> = MutableLiveData()


    override fun signInWithEmailAndPassword(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginTask ->
                signInResultLiveData.postValue(loginTask.isSuccessful)

            }
            .addOnFailureListener {
                signInResultLiveData.postValue(false)
            }

    override fun signInWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val userData = User(it.uid, it.displayName ?: "")
                        userLiveData.postValue(userData)
                        signInResultLiveData.postValue(true)
                    }
                } else {
                    signInResultLiveData.postValue(false)
                }
            }
            .addOnFailureListener {
                signInResultLiveData.postValue(false)
            }
    }

    override fun signupWithEmailAndPassword(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("in the func", "function")

                    val firebaseUser = task.result?.user
                    val user_id = firebaseUser?.uid ?: ""

                    // Create a user document in Firestore
                    val user = hashMapOf(
                        "id" to user_id,
                        "name" to name,
                        "email" to email,
                        "password" to password
                    )

                    val document = db.collection("users").document(user_id)
                    document.set(user)
                        .addOnSuccessListener {
                            signupResultLiveData.postValue(true)
                        }
                        .addOnFailureListener {
                            signupResultLiveData.postValue(false)
                        }
                } else {
                    // If createUserWithEmailAndPassword fails
                    signupResultLiveData.postValue(false)
                }
            }.addOnFailureListener {
                // If an exception occurs during the operation
                signupResultLiveData.postValue(false)
            }
    }


    override fun signupWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val userData = User(it.uid, it.displayName ?: "")
                        userLiveData.postValue(userData)
                        signupResultLiveData.postValue(true)
                    }
                } else {
                    signupResultLiveData.postValue(false)
                }
            }
            .addOnFailureListener {
                signupResultLiveData.postValue(false)
            }
    }

}

