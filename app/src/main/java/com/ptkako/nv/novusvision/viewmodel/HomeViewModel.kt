package com.ptkako.nv.novusvision.viewmodel

import androidx.lifecycle.ViewModel
import com.ptkako.nv.novusvision.repository.HomeRepository

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    val movieListLiveData = repository.movieListLiveData
    val movieInfoLiveData = repository.movieInfoLiveData
    val contentLiveData = repository.contentLiveData


    suspend fun getMovieList() = repository.getMovieList()

    suspend fun getMovieInfo(movieInfoId: Int) = repository.getMovieInfo(movieInfoId)

    suspend fun getContent(contentId: Int) = repository.getContent(contentId)

}