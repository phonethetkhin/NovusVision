package com.ptkako.nv.novusvision.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.model.SeriesModel
import com.ptkako.nv.novusvision.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    //tabControlVariable
    lateinit var tabPositionLiveData: MutableLiveData<Int>

    //HomeFragmentVariables
    private lateinit var trendingList: ArrayList<MovieModel>
    private lateinit var continueWatchingList: ArrayList<MovieModel>
    private lateinit var newMoviesList: ArrayList<MovieModel>
    private lateinit var recommendedList: ArrayList<MovieModel>
    var pgbHome = View.VISIBLE
    var nsvHome = View.GONE
    var dataStatus = 0
    private lateinit var _movieListLiveData: MutableLiveData<ArrayList<MovieModel>>

    //movieFragmentVariables----------------------------------------------------------------------//
    private lateinit var popularMovieList: ArrayList<MovieModel>
    private lateinit var newMovieList: ArrayList<MovieModel>
    private lateinit var allMovieList: ArrayList<MovieModel>
    private lateinit var combinedMovieList : ArrayList<ArrayList<MovieModel>>
    private lateinit var movieListLiveData: MutableLiveData<ArrayList<ArrayList<MovieModel>>>
    var pgbMovie = View.VISIBLE
    var nsvMovie = View.GONE

    //seriesFragmentVariables
    private lateinit var popularSeriesList: ArrayList<SeriesModel>
    private lateinit var newSeriesList: ArrayList<SeriesModel>
    private lateinit var allSeriesList: ArrayList<SeriesModel>
    private var combinedSeriesList = ArrayList<ArrayList<SeriesModel>>()
    private lateinit var seriesListLiveData: MutableLiveData<ArrayList<ArrayList<SeriesModel>>>
    var pgbSeries = View.VISIBLE
    var nsvSeries = View.GONE

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

    //home---------------------------------------------------------------------------------------//
    fun getHomeMovieListLiveData(): LiveData<ArrayList<MovieModel>> {
        if (!::_movieListLiveData.isInitialized) {
            _movieListLiveData = MutableLiveData<ArrayList<MovieModel>>()
            downloadMoviesForHome()
        }
        return _movieListLiveData
    }

    private fun downloadMoviesForHome() {
        viewModelScope.launch(Dispatchers.IO) {

            val allMoves = async { repository.getMovieListForHome(null) }
            val trending = async { repository.getMovieListForHome("P") }
            val newMovies = async { repository.getMovieListForHome("N") }

            trendingList = trending.await()
            continueWatchingList = allMoves.await()
            newMoviesList = newMovies.await()

            withContext(Dispatchers.Main) {
                getMovesListForHome(1)
            }
        }

    }

    fun getMovesListForHome(status: Int) {
        _movieListLiveData.value = when (status) {

            1 -> {
                dataStatus = 1
                trendingList
            }
            2 -> {
                dataStatus = 2
                continueWatchingList
            }
            else -> {
                dataStatus = 3
                newMoviesList
            }
        }
    }


    // movie-------------------------------------------------------------------------------------//
    fun getMovieListLiveData(): LiveData<ArrayList<ArrayList<MovieModel>>> {
        if (!::movieListLiveData.isInitialized) {
            Log.d("pgb", "init")
            movieListLiveData = MutableLiveData<ArrayList<ArrayList<MovieModel>>>()
            getMovieList()
        }
        Log.d("pgb", "notInit")
        return movieListLiveData
    }

    private fun getMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            combinedMovieList = ArrayList<ArrayList<MovieModel>>()
            popularMovieList = withContext(Dispatchers.IO) { repository.getMovieList("P") }
            combinedMovieList.add(popularMovieList)

            newMovieList = withContext(Dispatchers.IO) { repository.getMovieList("N") }
            combinedMovieList.add(newMovieList)

            allMovieList = withContext(Dispatchers.IO) { repository.getMovieList(null) }
            combinedMovieList.add(allMovieList)

            movieListLiveData.postValue(combinedMovieList)
            pgbMovie = View.GONE
            nsvMovie = View.VISIBLE
        }
    }

    //series--------------------------------------------------------------------------------------//
    fun getSeriesListLiveData(): LiveData<ArrayList<ArrayList<SeriesModel>>> {
        if (!::seriesListLiveData.isInitialized) {
            seriesListLiveData = MutableLiveData<ArrayList<ArrayList<SeriesModel>>>()
            viewModelScope.launch { getSeriesList() }
        }
        return seriesListLiveData
    }

    private fun getSeriesList() {
        viewModelScope.launch {
            popularSeriesList = withContext(Dispatchers.IO) { repository.getSeriesList("P") }
            combinedSeriesList.add(popularSeriesList)

            newSeriesList = withContext(Dispatchers.IO) { repository.getSeriesList("N") }
            combinedSeriesList.add(newSeriesList)

            allSeriesList = withContext(Dispatchers.IO) { repository.getSeriesList(null) }
            combinedSeriesList.add(allSeriesList)

            seriesListLiveData.postValue(combinedSeriesList)
            pgbSeries = View.GONE
            nsvSeries = View.VISIBLE
        }
    }

}

