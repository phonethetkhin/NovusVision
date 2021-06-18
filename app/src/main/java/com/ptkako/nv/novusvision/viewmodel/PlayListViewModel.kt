package com.ptkako.nv.novusvision.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.repository.PlayListRepository
import kotlinx.coroutines.launch

class PlayListViewModel(private val repository: PlayListRepository) : ViewModel() {
    private lateinit var movieListLiveData: MutableLiveData<ArrayList<MovieModel>>
    var pgbPlaylist = View.VISIBLE
    var rcvPlaylist = View.GONE

    fun getPlayListLiveData(userId: String): LiveData<ArrayList<MovieModel>> {
        if (!::movieListLiveData.isInitialized) {
            movieListLiveData = MutableLiveData<ArrayList<MovieModel>>()
            getPlayList(userId)
        }
        return movieListLiveData
    }

    private fun getPlayList(userId: String) {
        viewModelScope.launch {
            movieListLiveData.postValue(repository.getPlaylistByUser(userId))
        }
    }

}