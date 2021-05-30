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
    lateinit var allFinishLiveData: MutableLiveData<Int>
    lateinit var allMovieListLiveData: MutableLiveData<ArrayList<MovieModel>>
    lateinit var popularMovieListLiveData: MutableLiveData<ArrayList<MovieModel>>
    lateinit var newMovieListLiveData: MutableLiveData<ArrayList<MovieModel>>
    lateinit var seriesListLiveData: MutableLiveData<ArrayList<SeriesModel>>


    fun getFinishLiveData(): LiveData<Int> {
        if (!::allFinishLiveData.isInitialized) {
            allFinishLiveData = MutableLiveData(0)
        }
        return allFinishLiveData
    }

    fun setFinishLiveData(value:Int){
        if(::allFinishLiveData.isInitialized)
        {
            allFinishLiveData.postValue(value)
        }
    }

    //movies
    fun getAllMovieListLiveData(): LiveData<ArrayList<MovieModel>> {
        if (!::allMovieListLiveData.isInitialized) {
            allMovieListLiveData = MutableLiveData<ArrayList<MovieModel>>()
            viewModelScope.launch { getAllMovieList() }
        }
        return allMovieListLiveData
    }

    private suspend fun getAllMovieList() {
        allMovieListLiveData.postValue(repository.getAllMovieList())
    }

    fun getPopularMovieLiveData(): LiveData<ArrayList<MovieModel>> {
        if (!::popularMovieListLiveData.isInitialized) {
            popularMovieListLiveData = MutableLiveData<ArrayList<MovieModel>>()
            viewModelScope.launch { getPopularMovieList() }
        }
        return popularMovieListLiveData
    }


    private suspend fun getPopularMovieList() {
        popularMovieListLiveData.postValue(repository.getPopularMovieList())
    }


    fun getNewMovieListLiveData(): LiveData<ArrayList<MovieModel>> {
        if (!::newMovieListLiveData.isInitialized) {
            newMovieListLiveData = MutableLiveData<ArrayList<MovieModel>>()
            viewModelScope.launch { getNewMovieList() }
        }
        return newMovieListLiveData
    }


    private suspend fun getNewMovieList() {
        newMovieListLiveData.postValue(repository.getNewMovieList())
    }

    //--------------------------------------------------------------------------------------------------------------------------//
    //series
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

