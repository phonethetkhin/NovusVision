@file:Suppress("UNCHECKED_CAST")

package com.ptkako.nv.novusvision.repository

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.ptkako.nv.novusvision.model.EpisodeModel
import com.ptkako.nv.novusvision.utility.showToast
import kotlinx.coroutines.tasks.await

class SeriesDetailRepository(private val context: Context, private val fireStore: FirebaseFirestore) {


    suspend fun getSeasonIds(seriesId: String): ArrayList<String> {
        val seasonIdList = ArrayList<String>()
        val docRef = fireStore.collection("Series").document(seriesId).collection("Season").get()
        docRef.await()
        if (docRef.isSuccessful) {
            docRef.result!!.documents.forEach()
            {
                seasonIdList.add(it.id)
            }
        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }
        return seasonIdList
    }

    suspend fun getEpisodeList(seriesId: String, seasonId: String): ArrayList<EpisodeModel> {
        val episodeList = ArrayList<EpisodeModel>()
        val docRef = fireStore.collection("Series").document(seriesId).collection("Season").document(seasonId).get()
        docRef.await()
        if (docRef.isSuccessful) {
            val responseList = docRef.result!!.get("episodes") as ArrayList<HashMap<*, *>>
            responseList.forEach {
                val id = it["episode_id"] as String
                val des = it["episode_description"] as String
                val photo = it["episode_photo"] as String
                val title = it["episode_title"] as String
                val url = it["episode_url"] as String
                episodeList.add(EpisodeModel(id, des, photo, title, url))
            }
        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }
        return episodeList
    }
}