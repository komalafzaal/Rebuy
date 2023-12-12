package com.example.rebuy

import com.example.rebuy.Model.data.CommentResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface myApi {
    @GET("/comments")
    fun getComments(): Call<CommentResponse>
}