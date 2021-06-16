package com.ptkako.nv.novusvision.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.model.CombinedModel
import com.ptkako.nv.novusvision.repository.PlayListRepository
import kotlinx.coroutines.launch

class PlayListViewModel(private val repository: PlayListRepository) : ViewModel() {
    private lateinit var combinedListLiveData: MutableLiveData<ArrayList<CombinedModel>>

    fun getPlayListLiveData(userId: String): LiveData<ArrayList<CombinedModel>> {
        if (!::combinedListLiveData.isInitialized) {
            combinedListLiveData = MutableLiveData<ArrayList<CombinedModel>>()
            getPlayList(userId)
        }
        return combinedListLiveData
    }

    private fun getPlayList(userId: String) {
        viewModelScope.launch {
            combinedListLiveData.postValue(repository.getPlaylistByUser(userId))
        }
    }

}