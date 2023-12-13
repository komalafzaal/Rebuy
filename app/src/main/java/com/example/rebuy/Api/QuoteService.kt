package com.example.rebuy.Api

import com.example.rebuy.Model.data.QuoteList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteService {
//    @GET("/quotes")
//    fun getQuotes(@Query("page") page: Int): Call<QuoteList>

    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): Response<QuoteList>
}
