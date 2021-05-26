package com.ptkako.nv.novusvision.repository

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.model.SeriesModel
import com.ptkako.nv.novusvision.utility.showToast
import kotlinx.coroutines.tasks.await

class HomeRepository(private val context: Context, private val fireStore: FirebaseFirestore) {

    suspend fun getMovieList(): ArrayList<MovieModel> {
        val movieList = ArrayList<MovieModel>()
        val docRef = fireStore.collection("Movie").whereEqualTo("is_series", false).get()
        docRef.await()
        if (docRef.isSuccessful) {
            docRef.result!!.forEach {
                movieList.add(it.toObject())
            }
        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }

        return movieList
    }

    suspend fun getSeriesList(): ArrayList<SeriesModel> {
        val seriesList = ArrayList<SeriesModel>()
        val docRef = fireStore.collection("Movie").whereEqualTo("is_series", true).get()
        docRef.await()
        if (docRef.isSuccessful) {
            docRef.result!!.forEach {
                seriesList.add(it.toObject())
            }
        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }
        return seriesList
    }
}