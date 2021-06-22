package com.ptkako.nv.novusvision.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.repository.MovieDetailRepository
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: MovieDetailRepository) : ViewModel() {
    private lateinit var documentIdLiveData: MutableLiveData<String>

    //home---------------------------------------------------------------------------------------//
    fun getDocumentIdLiveData(movieName: String): LiveData<String> {
        if (!::documentIdLiveData.isInitialized) {
            Log.d("pgb", "init")
            documentIdLiveData = MutableLiveData<String>()
            getDocumentId(movieName)
        }
        Log.d("pgb", "notInit")
        return documentIdLiveData
    }

    fun getDocumentId(movieName: String) {
        viewModelScope.launch {
            documentIdLiveData.postValue(repository.getMovieDocumentId(movieName))
        }
    }

    fun addPlayList(playlist: HashMap<String, String>) = viewModelScope.launch {
        repository.addPlaylist(playlist)
    }

    fun addHistory(history: HashMap<String, Any>) = viewModelScope.launch {
        repository.addToHistory(history)
    }

    fun updateHistory(historyId:String, lastWatch:String, lastPlayedTime:String) = viewModelScope.launch {
        repository.updateHistory(historyId, lastWatch, lastPlayedTime)
    }

    suspend fun checkExistingPlaylist(movieId: String, userId: String): Any? {
        return repository.checkExistingPlaylist(movieId, userId)
    }

    suspend fun checkExistingHistory(movieId: String, userId: String): Any? {
        return repository.checkExistingHistory(movieId, userId)
    }

}