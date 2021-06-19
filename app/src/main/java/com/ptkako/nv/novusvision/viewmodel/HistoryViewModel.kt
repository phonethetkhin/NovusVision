package com.ptkako.nv.novusvision.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    private lateinit var movieListLiveData: MutableLiveData<ArrayList<MovieModel>>
    var pgbHistory = View.VISIBLE
    var rcvHistory = View.GONE

    fun getHistoryListLiveData(userId: String): LiveData<ArrayList<MovieModel>> {
        if (!::movieListLiveData.isInitialized) {
            movieListLiveData = MutableLiveData<ArrayList<MovieModel>>()
            getHistory(userId)
        }
        return movieListLiveData
    }

    private fun getHistory(userId: String) {
        viewModelScope.launch {
            movieListLiveData.postValue(repository.getHistoryListByUser(userId))
        }
    }

}