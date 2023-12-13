package com.example.rebuy.Model.Repository

import androidx.lifecycle.MutableLiveData
import com.example.rebuy.Model.data.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthRepository @Inject constructor() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val signInResultLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val userLiveData: MutableLiveData<User> = MutableLiveData()
    val db = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun signInWithEmailAndPassword(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginTask ->
                signInResultLiveData.postValue(loginTask.isSuccessful)
                if (loginTask.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val userId = it.uid
                        val userRef = db.collection("users").document(userId)

                        userRef.get().addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                val userName = documentSnapshot.getString("name")
                                val userData = User(userId, userName.toString())
                                userLiveData.postValue(userData)
                                signInResultLiveData.postValue(true)
                            } else {
                                signInResultLiveData.postValue(false)
                            }
                        }
                    }
                } else {
                    signInResultLiveData.postValue(false)
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure in signing in
            }

    fun signInWithGoogle(account: GoogleSignInAccount) {
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
}

