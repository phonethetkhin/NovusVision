package com.ptkako.nv.novusvision.repository

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
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
    /*fun getEpisodeList()
    {
        docRef.result!!.documents.forEach()
        {
            val map = it.data!!
            map.forEach()
            {
                if (it.key == "episodes") {
                    Log.d("seasonNum", it.value.toString())
                }
            }
        }
    }*/
}