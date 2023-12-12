package com.example.rebuy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rebuy.Model.data.CommentResponse
import com.example.rebuy.Model.data.Comments
import com.example.rebuy.databinding.ActivityOkHttpBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class OkHttp : AppCompatActivity() {
    private lateinit var binding: ActivityOkHttpBinding
    private val BASE_URL = "https://reqres.in/api"
    private val URL = "https://dummyjson.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOkHttpBinding.inflate(layoutInflater) // Initialize binding
        setContentView(binding.root)
        binding.get.setOnClickListener() {
            get_response()
        }
        binding.post.setOnClickListener {
            post_request()
        }
        getAllComments()
    }

    private fun get_response() {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://reqres.in/api/users?page=2")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("Error", "Error")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody = response.body()?.string()

                    runOnUiThread {
                        binding.responseTextview.text = responseBody
                    }

//                    binding.responseTextview.setText(responseBody)
                    Log.d("Response", "${responseBody}")
                }
            }
        })
    }

    //create post request-- https://reqres.in/api/users
    private fun post_request() {
        val client = OkHttpClient()
        val name = binding.name.text.toString()
        val job = binding.job.text.toString()

        val formBody: RequestBody = FormBody.Builder()
            .add("name", name)
            .add("job", job)
            .build()

        val request: Request = Request.Builder()
            .url("$BASE_URL/users")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    binding.serverResponse.text = "Request failed: ${e.message}"
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        runOnUiThread {
                            binding.serverResponse.text = "Unexpected code: ${response.code()}"
                        }
                        return
                    }
                    val responseBody = response.body()?.string()

                    runOnUiThread {
                        binding.serverResponse.text = responseBody
                    }
                }
            }
        })
    }


    //get comments from api using Retrofit
    private fun getAllComments() {
        val api = Retrofit.Builder()
            .baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(myApi::class.java)

        api.getComments().enqueue(object: retrofit2.Callback<CommentResponse> {
            override fun onResponse(
                call: retrofit2.Call<CommentResponse>,
                response: retrofit2.Response<CommentResponse>
            ) {
                if (response.isSuccessful) {
                    val commentResponse = response.body()
                    Log.d("OnResponse:", "Comment: ${commentResponse}")
                    // Handle each comment as needed
                } else {
                    Log.d("OnResponse:", "Response unsuccessful")
                }
            }

            override fun onFailure(call: retrofit2.Call<CommentResponse>, t: Throwable) {
                Log.d("Status:", "FailedResponse ${t.message}")
            }

        })
    }

}



