//package com.example.rebuy
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.rebuy.ui.Home
//import com.example.rebuy.ui.Login
//import com.google.firebase.auth.OAuthProvider
//
//
//class TwitterActivity : AppCompatActivity() {
////    companion object {
////        lateinit var firebaseAuth: FirebaseAuth
////    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
////        firebaseAuth = FirebaseAuth.getInstance()
//        val provider = OAuthProvider.newBuilder("twitter.com")
////        provider.addCustomParameter("lang", "fr")
//
//        val pendingResultTask = Login.auth.pendingAuthResult
//        if (pendingResultTask != null) {
//            pendingResultTask.addOnSuccessListener {
//                    startActivity(Intent(this, Home::class.java))
//                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show()
//                }
//                .addOnFailureListener {
//                    // Handle failure.
//                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
//                }
//        } else {
//            Login.auth.startActivityForSignInWithProvider(this, provider.build())
//                .addOnSuccessListener {
//                    startActivity(Intent(this, Home::class.java))
//                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
//                }
//        }
//    }
//}
