package com.example.rebuy

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rebuy.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore


class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val request_code = 1001
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater) // Initialize binding
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.client_id))
        .requestEmail()
        .build()

        val googleSigninClient = GoogleSignIn.getClient(this, gso)


        binding.navigateToLogin.setOnClickListener {
            val intent = Intent(this@SignUp, Login::class.java)
            startActivity(intent)
        }

        binding.createAccount.setOnClickListener {
            val name = binding.userName.text.toString().trim()
            val email = binding.userEmail.text.toString().trim()
            val password = binding.userPassowrd.text.toString().trim()

//            val user_id = FirebaseAuth.getInstance().currentUser!!.uid

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                Login.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result?.user
                        val user_id = firebaseUser?.uid ?: ""

                        val user = hashMapOf<String, Any>(
                            "id" to user_id,
                            "name" to name,
                            "email" to email,
                            "password" to password
                        )

                        db.collection("users").document(user_id)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot added with ID: $user_id")
                            }
                            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }

                        startActivity(Intent(this@SignUp, Login::class.java))
                        Toast.makeText(this, "Signup Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.googleBtn.setOnClickListener {
            googleSigninClient.signOut()     // when 2nd time you click last email will set and pop up not show thats why 1ds logout
            startActivityForResult(googleSigninClient.signInIntent, request_code)

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
        Login.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        val userName = user.displayName
                        val intent = Intent(this, Home::class.java)
                        intent.putExtra("USERNAME", userName)
                        startActivity(intent)
                        Toast.makeText(this, "Sign up Successfully", Toast.LENGTH_SHORT).show()
                    }

                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}