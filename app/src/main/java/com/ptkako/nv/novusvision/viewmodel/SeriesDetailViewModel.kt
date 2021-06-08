package com.ptkako.nv.novusvision.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.model.EpisodeModel
import com.ptkako.nv.novusvision.repository.SeriesDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeriesDetailViewModel(private val repository: SeriesDetailRepository) : ViewModel() {
    lateinit var seasonIdsLiveData: MutableLiveData<ArrayList<String>>
    lateinit var episodeListLiveData: MutableLiveData<ArrayList<EpisodeModel>>
    private lateinit var seasonIdList: ArrayList<String>
    private lateinit var episodeList: ArrayList<EpisodeModel>
    var pgbSeriesDetail = View.VISIBLE
    var nsvSeriesDetail = View.GONE
    var currentSeasonId = 0
    var seasonId = 1
    var rowIndex = 0




    fun getSeasonIdLiveData(seriesId: String): LiveData<ArrayList<String>> {
        if (!::seasonIdsLiveData.isInitialized) {
            seasonIdsLiveData = MutableLiveData<ArrayList<String>>()
            getSeasonIds(seriesId)
        }
        return seasonIdsLiveData
    }

    private fun getSeasonIds(seriesId: String) {
        viewModelScope.launch {
            seasonIdList = withContext(Dispatchers.IO) { repository.getSeasonIds(seriesId) }
            seasonIdsLiveData.postValue(seasonIdList)
        }
    }

    fun getEpisodeListLiveData(seriesId: String): LiveData<ArrayList<EpisodeModel>> {
        if (!::episodeListLiveData.isInitialized || currentSeasonId != seasonId) {
            currentSeasonId = seasonId
            Log.d("sid", "init")
            Log.d("sid", "$currentSeasonId")
            Log.d("sid", "$seasonId")

            episodeListLiveData = MutableLiveData<ArrayList<EpisodeModel>>()
            getEpisodeList(seriesId, seasonId.toString())
        }
        Log.d("sid", "not init")

        return episodeListLiveData
    }


    private fun getEpisodeList(seriesId: String, seasonId: String) {
        viewModelScope.launch {
            episodeList = withContext(Dispatchers.IO) { repository.getEpisodeList(seriesId, seasonId) }
            episodeListLiveData.postValue(episodeList)
            pgbSeriesDetail = View.GONE
            nsvSeriesDetail = View.VISIBLE
        }
    }

}