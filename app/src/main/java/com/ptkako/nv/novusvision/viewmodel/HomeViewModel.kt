package com.ptkako.nv.novusvision.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    lateinit var movieListLiveData: MutableLiveData<Any>
    lateinit var movieInfoLiveData: MutableLiveData<Any>
    lateinit var contentLiveData: MutableLiveData<Any>

    fun getMovieListLiveData(): LiveData<Any> {
        Log.d("lifecycle", "init")
        if (!::movieListLiveData.isInitialized) {
            Log.d("lifecycle", "not init")
            movieListLiveData = MutableLiveData<Any>()
            viewModelScope.launch { getMovieListData() }
        }
        return movieListLiveData
    }

    private suspend fun getMovieListData() {
        Log.d("lifecycle", "getMovieList")
        Log.d("lifecycle", repository.getMovieList().toString())
        movieListLiveData.postValue(repository.getMovieList())
    }

    fun getMovieInfoLiveData(movieInfoId: Int): LiveData<Any> {
        if (!::movieInfoLiveData.isInitialized) {
            movieInfoLiveData = MutableLiveData<Any>()
            viewModelScope.launch { getMovieInfoData(movieInfoId) }
        }
        return movieInfoLiveData
    }

    private suspend fun getMovieInfoData(movieInfoId: Int) {
        movieInfoLiveData.postValue(repository.getMovieInfo(movieInfoId))
    }


    fun getContentLiveData(contentId: Int): LiveData<Any> {
        if (!::contentLiveData.isInitialized) {
            contentLiveData = MutableLiveData<Any>()
            viewModelScope.launch { getContentData(contentId) }
        }
        return contentLiveData
    }

    suspend fun getContentData(contentId: Int) {
        contentLiveData.postValue(repository.getContent(contentId))
    }
}

