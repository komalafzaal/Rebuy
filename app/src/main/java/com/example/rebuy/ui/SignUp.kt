package com.example.rebuy.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rebuy.R
import com.example.rebuy.ViewModels.LoginViewModel
import com.example.rebuy.ViewModels.SignupViewModel
import com.example.rebuy.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignupViewModel
    private val request_code = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater) // Initialize binding
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        binding.navigateToLogin.setOnClickListener {
            val intent = Intent(this@SignUp, Login::class.java)
            startActivity(intent)
        }

        binding.createAccount.setOnClickListener {
            val name = binding.userName.text.toString().trim()
            val email = binding.userEmail.text.toString().trim()
            val password = binding.userPassowrd.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signupWithEmailAndPassword(email, password, name)

            }
        }


        binding.googleBtn.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build()

            val googleSigninClient = GoogleSignIn.getClient(this, gso)

            googleSigninClient.signOut()     // when 2nd time you click last email will set and pop up not show thats why 1ds logout
            startActivityForResult(googleSigninClient.signInIntent, request_code)

        }

        viewModel.signupResultLiveData.observe(this, Observer { isSuccessful ->
            if (isSuccessful) {
                val intent = Intent(this, Home::class.java)
                val user = viewModel.userLiveData.value
                val userName = user?.name ?: ""
                intent.putExtra("USERNAME", userName)
                startActivity(intent)
                Toast.makeText(this, "Signup Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "INVALID!! Signup Failed", Toast.LENGTH_SHORT).show()

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
            viewModel.signupWithGoogle(account)
        }
    }
}
