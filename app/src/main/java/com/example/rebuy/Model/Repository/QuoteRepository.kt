package com.example.rebuy.Model.Repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rebuy.Api.QuoteService
import com.example.rebuy.Model.data.QuoteList
import retrofit2.Response
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val quoteService: QuoteService) {

    private val _quotesLiveData = MutableLiveData<QuoteList>()
    val quotesLiveData: LiveData<QuoteList> = _quotesLiveData

    //-----------with Call and using enqueue method------------------------
//    fun getQuotes(page: Int) {
//        quoteService.getQuotes(page).enqueue(object : Callback<QuoteList> {
//            @SuppressLint("NullSafeMutableLiveData")
//            override fun onResponse(call: Call<QuoteList>, response: Response<QuoteList>) {
//                if (response.isSuccessful) {
//                    val quotes: QuoteList? = response.body()
//                    _quotesLiveData.postValue(quotes!!)
//                    Log.d("QuotesSuccess", "Quotes fetched successfully")
//                } else {
//                    Log.e(
//                        "QuotesFailed",
//                        "Failed to fetch quotes. Error: ${response.code()} - ${response.message()}"
//                    )
//                    _quotesLiveData.postValue(null)
//                }
//            }
//
//            @SuppressLint("NullSafeMutableLiveData")
//            override fun onFailure(call: Call<QuoteList>, t: Throwable) {
//                Log.e("QuotesFailed", "Exception while fetching quotes: ${t.message}")
//                _quotesLiveData.postValue(null)
//            }
//        })
//    }


    //-----------using co routine suspend fucntion------------------
    @SuppressLint("NullSafeMutableLiveData")
    suspend fun getQuotes(page: Int) {
        try {
            val response: Response<QuoteList> = quoteService.getQuotes(page)
            if (response.isSuccessful) {
                val quotes: QuoteList? = response.body()
                _quotesLiveData.postValue(quotes!!)
                Log.d("QuotesSuccess", "Quotes fetched successfully")
            } else {
                Log.e("QuotesFailed", "Failed to fetch quotes. Error: ${response.code()} - ${response.message()}")
                _quotesLiveData.postValue(null)
            }
        } catch (e: Exception) {
            Log.e("QuotesFailed", "Exception while fetching quotes: ${e.message}")
            _quotesLiveData.postValue(null)
        }
    }
}


