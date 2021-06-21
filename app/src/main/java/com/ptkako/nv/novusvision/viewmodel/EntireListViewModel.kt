package com.ptkako.nv.novusvision.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EntireListViewModel(private val repository: HomeRepository) : ViewModel() {
    lateinit var moviesListLiveData: MutableLiveData<ArrayList<MovieModel>>
    val chipTextList = ArrayList<String>()

    var pgbEntire = View.VISIBLE

    fun getMoviesListLiveData(statusCode: String?): LiveData<ArrayList<MovieModel>> {
        if (!::moviesListLiveData.isInitialized) {
            moviesListLiveData = MutableLiveData<ArrayList<MovieModel>>()
            getMoviesList(statusCode)
        }
        return moviesListLiveData
    }

    private fun getMoviesList(statusCode: String?) {
        viewModelScope.launch {
            moviesListLiveData.value = withContext(Dispatchers.IO) { repository.getMovieList(statusCode) }
        }
    }

}