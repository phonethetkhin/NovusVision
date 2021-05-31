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
    //tabControlVariable
    lateinit var tabPositionLiveData : MutableLiveData<Int>

    //fetchFinishControlVariable
    lateinit var allFinishLiveData: MutableLiveData<Int>

    //moviesVariables
    lateinit var allMovieListLiveData: MutableLiveData<ArrayList<MovieModel>>
    lateinit var popularMovieListLiveData: MutableLiveData<ArrayList<MovieModel>>
    lateinit var newMovieListLiveData: MutableLiveData<ArrayList<MovieModel>>

    //seriesVariables
    lateinit var allSeriesListLiveData: MutableLiveData<ArrayList<SeriesModel>>
    lateinit var popularSeriesListLiveData: MutableLiveData<ArrayList<SeriesModel>>
    lateinit var newSeriesListLiveData: MutableLiveData<ArrayList<SeriesModel>>
    //tabPositionControl--------------------------------------------------------------------------//
    fun getTabPositionLiveData(): LiveData<Int> {
        if (!::tabPositionLiveData.isInitialized) {
            tabPositionLiveData = MutableLiveData(0)
        }
        return tabPositionLiveData
    }

    fun setTabPositionLiveData(value: Int) {
        if (::tabPositionLiveData.isInitialized) {
            tabPositionLiveData.postValue(value)
        }
    }

    //fetchFinishControl--------------------------------------------------------------------------//
    fun getFinishLiveData(): LiveData<Int> {
        if (!::allFinishLiveData.isInitialized) {
            allFinishLiveData = MutableLiveData(0)
        }
        return allFinishLiveData
    }

    fun setFinishLiveData(value: Int) {
        if (::allFinishLiveData.isInitialized) {
            allFinishLiveData.postValue(value)
        }
    }

    //movies---------------------------------------------------------------------------------------//
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

    //series--------------------------------------------------------------------------------------//
    fun getAllSeriesListLiveData(): LiveData<ArrayList<SeriesModel>> {
        if (!::allSeriesListLiveData.isInitialized) {
            allSeriesListLiveData = MutableLiveData<ArrayList<SeriesModel>>()
            viewModelScope.launch { getAllSeriesListData() }
        }
        return allSeriesListLiveData
    }

    private suspend fun getAllSeriesListData() {
        allSeriesListLiveData.postValue(repository.getAllSeriesList())
    }

    fun getPopularSeriesListLiveData(): LiveData<ArrayList<SeriesModel>> {
        if (!::popularSeriesListLiveData.isInitialized) {
            popularSeriesListLiveData = MutableLiveData<ArrayList<SeriesModel>>()
            viewModelScope.launch { getPopularSeriesListData() }
        }
        return popularSeriesListLiveData
    }

    private suspend fun getPopularSeriesListData() {
        popularSeriesListLiveData.postValue(repository.getPopularSeriesList())
    }

    fun getNewSeriesListLiveData(): LiveData<ArrayList<SeriesModel>> {
        if (!::newSeriesListLiveData.isInitialized) {
            newSeriesListLiveData = MutableLiveData<ArrayList<SeriesModel>>()
            viewModelScope.launch { getNewSeriesListData() }
        }
        return newSeriesListLiveData
    }

    private suspend fun getNewSeriesListData() {
        newSeriesListLiveData.postValue(repository.getNewSeriesList())
    }

}

