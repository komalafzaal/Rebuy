package com.example.rebuy

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.rebuy.databinding.ActivityLoginBinding
import com.example.rebuy.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val request_code = 1001
    private val db = FirebaseFirestore.getInstance()


    companion object {
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) // Initialize binding

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        val googleSigninClient = GoogleSignIn.getClient(this, gso)

        binding.navigateToSignup.setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val password = binding.userPassowrd.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { loginTask->
                    if (loginTask.isSuccessful) {
                        val currentUser = auth.currentUser
                        currentUser?.let { user ->
                            val userId = user.uid
                            val userRef = db.collection("users").document(userId)

                            userRef.get().addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val userName = document.getString("name")
                                    userName?.let {
                                        val intent = Intent(this@Login, Home::class.java)
                                        intent.putExtra("USERNAME", it) // Pass the username
                                        startActivity(intent)
                                        finish()
                                    }
                                } else { Log.d(TAG, "No such document") }
                            }.addOnFailureListener { exception -> Log.d(TAG, "get failed with ", exception) }
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.googleBtn.setOnClickListener {
            googleSigninClient.signOut()     // when 2nd time you click last email will set and pop up not show thats why 1ds logout
            startActivityForResult(googleSigninClient.signInIntent, request_code)

        }
        binding.twitterBtn.setOnClickListener {
            startActivity(Intent(this@Login, TwitterActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == request_code && resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        val userName = user.displayName
                        val intent = Intent(this, Home::class.java)
                        intent.putExtra("USERNAME", userName)
                        startActivity(intent)
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }


}