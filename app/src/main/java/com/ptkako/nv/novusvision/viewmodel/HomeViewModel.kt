package com.ptkako.nv.novusvision.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private lateinit var popularHomeList: ArrayList<MovieModel>
    private lateinit var newHomeList: ArrayList<MovieModel>
    private lateinit var allHomeList: ArrayList<MovieModel>
    private lateinit var homeListLiveData: MutableLiveData<ArrayList<ArrayList<MovieModel>>>
    var pgbHome = View.VISIBLE
    var nsvHome = View.GONE

    //movieFragmentVariables----------------------------------------------------------------------//
    private lateinit var popularMovieList: ArrayList<MovieModel>
    private lateinit var newMovieList: ArrayList<MovieModel>
    private lateinit var allMovieList: ArrayList<MovieModel>
    private lateinit var movieListLiveData: MutableLiveData<ArrayList<ArrayList<MovieModel>>>
    var pgbMovie = View.VISIBLE
    var nsvMovie = View.GONE

    //seriesFragmentVariables
    private lateinit var popularSeriesList: ArrayList<SeriesModel>
    private lateinit var newSeriesList: ArrayList<SeriesModel>
    private lateinit var allSeriesList: ArrayList<SeriesModel>
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
    fun getHomeListLiveData(): LiveData<ArrayList<ArrayList<MovieModel>>> {
        if (!::homeListLiveData.isInitialized) {
            Log.d("pgb", "init")
            homeListLiveData = MutableLiveData<ArrayList<ArrayList<MovieModel>>>()
            getHomeList(null)
        }
        Log.d("pgb", "notInit")
        return homeListLiveData
    }

    fun getHomeList(srlHome: SwipeRefreshLayout?) {
        viewModelScope.launch {
            popularHomeList = withContext(Dispatchers.IO) { repository.getMovieList("P") }
            newHomeList = withContext(Dispatchers.IO) { repository.getMovieList("N") }
            allHomeList = withContext(Dispatchers.IO) { repository.getMovieList(null) }
            homeListLiveData.postValue(arrayListOf(popularHomeList, newHomeList, allHomeList))

            pgbHome = View.GONE
            nsvHome = View.VISIBLE
            if (srlHome != null) {
                srlHome.isRefreshing = false
            }
        }
    }


    // movie-------------------------------------------------------------------------------------//
    fun getMovieListLiveData(): LiveData<ArrayList<ArrayList<MovieModel>>> {
        if (!::movieListLiveData.isInitialized) {
            Log.d("pgb", "init")
            movieListLiveData = MutableLiveData<ArrayList<ArrayList<MovieModel>>>()
            getMovieList(null)
        }
        Log.d("pgb", "notInit")
        return movieListLiveData
    }

    fun getMovieList(srlMovie: SwipeRefreshLayout?) {
        viewModelScope.launch {
            popularMovieList = withContext(Dispatchers.IO) { repository.getMovieList("P") }
            newMovieList = withContext(Dispatchers.IO) { repository.getMovieList("N") }
            allMovieList = withContext(Dispatchers.IO) { repository.getMovieList(null) }
            movieListLiveData.postValue(arrayListOf(popularMovieList, newMovieList, allMovieList))

            pgbMovie = View.GONE
            nsvMovie = View.VISIBLE
            if (srlMovie != null) {
                srlMovie.isRefreshing = false
            }
        }
    }

    //series--------------------------------------------------------------------------------------//
    fun getSeriesListLiveData(): LiveData<ArrayList<ArrayList<SeriesModel>>> {
        if (!::seriesListLiveData.isInitialized) {
            seriesListLiveData = MutableLiveData<ArrayList<ArrayList<SeriesModel>>>()
            getSeriesList(null)
        }
        return seriesListLiveData
    }

    fun getSeriesList(srlSeries:SwipeRefreshLayout?) {
        viewModelScope.launch {
            popularSeriesList = withContext(Dispatchers.IO) { repository.getSeriesList("P") }
            newSeriesList = withContext(Dispatchers.IO) { repository.getSeriesList("N") }
            allSeriesList = withContext(Dispatchers.IO) { repository.getSeriesList(null) }

            seriesListLiveData.postValue(arrayListOf(popularSeriesList, newSeriesList, allSeriesList))
            pgbSeries = View.GONE
            nsvSeries = View.VISIBLE
            if(srlSeries!=null)
            {
                srlSeries.isRefreshing = false
            }
        }
    }

}

