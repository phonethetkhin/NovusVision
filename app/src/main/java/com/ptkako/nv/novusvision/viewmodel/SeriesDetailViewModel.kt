package com.ptkako.nv.novusvision.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.repository.SeriesDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeriesDetailViewModel(private val repository: SeriesDetailRepository) : ViewModel() {
    lateinit var seriesDetailLiveData: MutableLiveData<ArrayList<ArrayList<String>>>
    private lateinit var seasonIdList: ArrayList<String>
    private lateinit var episodeList: ArrayList<String>
    var pgbSeriesDetail = View.VISIBLE
    var nsvSeriesDetail = View.GONE

    fun getSeriesDetailLiveData(seriesId: String, seasonId: String): LiveData<ArrayList<ArrayList<String>>> {
        if (!::seriesDetailLiveData.isInitialized) {
            seriesDetailLiveData = MutableLiveData<ArrayList<ArrayList<String>>>()
            getSeriesDetail(seriesId, seasonId)
        }
        return seriesDetailLiveData
    }

    private fun getSeriesDetail(seriesId: String, seasonId: String) {
        viewModelScope.launch {
            seasonIdList = withContext(Dispatchers.IO) { repository.getSeasonIds(seriesId) }
            episodeList = withContext(Dispatchers.IO) { repository.getEpisodeList(seriesId, seasonId) }
            seriesDetailLiveData.postValue(arrayListOf(seasonIdList, episodeList))
            pgbSeriesDetail = View.GONE
            nsvSeriesDetail = View.VISIBLE
        }
    }

}