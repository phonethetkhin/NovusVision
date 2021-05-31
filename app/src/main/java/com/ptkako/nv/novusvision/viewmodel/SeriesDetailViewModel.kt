package com.ptkako.nv.novusvision.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.repository.SeriesDetailRepository
import kotlinx.coroutines.launch

class SeriesDetailViewModel(private val repository: SeriesDetailRepository) : ViewModel() {
    //seasonNumberLiveData
    lateinit var seasonNumberListLiveData:MutableLiveData<ArrayList<String>>

    //seasonNumberLiveData------------------------------------------------------------------------//
    fun getSeasonNumberLiveData(seriesId: String): LiveData<ArrayList<String>> {
        if (!::seasonNumberListLiveData.isInitialized) {
            seasonNumberListLiveData = MutableLiveData<ArrayList<String>>()
            viewModelScope.launch { getAllMovieList(seriesId) }
        }
        return seasonNumberListLiveData
    }

    private suspend fun getAllMovieList(seriesId: String) {
        seasonNumberListLiveData.postValue(repository.getSeasonIds(seriesId))
    }

}