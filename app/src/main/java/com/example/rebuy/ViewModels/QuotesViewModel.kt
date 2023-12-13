package com.example.rebuy.ViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebuy.Model.Repository.QuoteRepository
import com.example.rebuy.Model.data.QuoteList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(private val repository: QuoteRepository) : ViewModel() {

    val quotesLiveData: LiveData<QuoteList> = repository.quotesLiveData

//    fun getQuotes(page: Int) {
//        repository.getQuotes(page)
//    }
    fun getQuotes(page: Int) {
        viewModelScope.launch {
            repository.getQuotes(page)
        }
    }
}
