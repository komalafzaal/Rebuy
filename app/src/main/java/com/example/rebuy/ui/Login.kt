package com.example.rebuy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rebuy.R
import com.example.rebuy.ViewModels.LoginViewModel
import com.example.rebuy.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private val request_code = 1001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) // Initialize binding
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        binding.navigateToSignup.setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }

//        binding.twitterBtn.setOnClickListener {
//            startActivity(Intent(this@Login, TwitterActivity::class.java))
//        }

        binding.googleBtn.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient.signInIntent
            googleSignInClient.signOut()
            startActivityForResult(signInIntent, request_code)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val password = binding.userPassowrd.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signInWithEmailAndPassword(email, password)
            }
        }

        viewModel.signInResultLiveData.observe(this, Observer { isSuccessful ->
            if (isSuccessful) {
                val intent = Intent(this, Home::class.java)
                val user = viewModel.userLiveData.value
                val userName = user?.name ?: ""
                intent.putExtra("USERNAME", userName)
                startActivity(intent)
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "INVALID!! Login Failed", Toast.LENGTH_SHORT).show()

            }
        })

        // Observe the LiveData for user data
        viewModel.userLiveData.observe(this, Observer { user ->
            val userName = user?.name ?: ""
            val userEmail = user?.uid ?: ""
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == request_code && resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            viewModel.signInWithGoogle(account)
        }
    }
}
