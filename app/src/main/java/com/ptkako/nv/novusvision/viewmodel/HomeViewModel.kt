package com.ptkako.nv.novusvision.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.model.SeriesModel
import com.ptkako.nv.novusvision.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    lateinit var movieListLiveData: MutableLiveData<ArrayList<MovieModel>>
    lateinit var seriesListLiveData: MutableLiveData<ArrayList<SeriesModel>>

    fun getMovieListLiveData(): LiveData<ArrayList<MovieModel>> {
        if (!::movieListLiveData.isInitialized) {
            movieListLiveData = MutableLiveData<ArrayList<MovieModel>>()
            viewModelScope.launch { getMovieListData() }
        }
        return movieListLiveData
    }

    private suspend fun getMovieListData() {
        movieListLiveData.postValue(repository.getMovieList())
    }

    fun getSeriesListLiveData(): LiveData<ArrayList<SeriesModel>> {
        if (!::seriesListLiveData.isInitialized) {
            seriesListLiveData = MutableLiveData<ArrayList<SeriesModel>>()
            viewModelScope.launch { getSeriesListData() }
        }
        return seriesListLiveData
    }

    private suspend fun getSeriesListData() {
        seriesListLiveData.postValue(repository.getSeriesList())
    }

}

