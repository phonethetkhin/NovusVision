package com.ptkako.nv.novusvision.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.model.SeriesModel
import com.ptkako.nv.novusvision.utility.showToast
import kotlinx.coroutines.tasks.await

class HomeRepository(private val context: Context, private val fireStore: FirebaseFirestore) {

    //movies
    suspend fun getAllMovieList(): ArrayList<MovieModel> {
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

    suspend fun getPopularMovieList(): ArrayList<MovieModel> {
        val movieList = ArrayList<MovieModel>()
        val docRef = fireStore.collection("Movie").whereEqualTo("is_series", false).whereEqualTo("status_code", "P").get()
        docRef.await()
        if (docRef.isSuccessful) {
            docRef.result
            docRef.result!!.forEach {
                movieList.add(it.toObject())
            }
        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }

        return movieList
    }

    suspend fun getNewMovieList(): ArrayList<MovieModel> {
        val movieList = ArrayList<MovieModel>()
        val docRef = fireStore.collection("Movie").whereEqualTo("is_series", false).whereEqualTo("status_code", "N").get()
        docRef.await()
        if (docRef.isSuccessful) {
            docRef.result
            docRef.result!!.forEach {
                movieList.add(it.toObject())
            }
        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }

        return movieList
    }

    //series
    suspend fun getAllSeriesList(): ArrayList<SeriesModel> {
        val seriesList = ArrayList<SeriesModel>()
        val docRef = fireStore.collection("Movie").whereEqualTo("is_series", true).get()
        docRef.await()
        if (docRef.isSuccessful) {
            Log.d("ref", "getAllSeriesList: ${docRef.result}")

            docRef.result!!.forEach {
                seriesList.add(it.toObject())
            }
            Log.d("ref", "getAll: $seriesList")

        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }
        return seriesList
    }

    suspend fun getPopularSeriesList(): ArrayList<SeriesModel> {
        val seriesList = ArrayList<SeriesModel>()
        val docRef = fireStore.collection("Movie").whereEqualTo("is_series", true).whereEqualTo("status_code", "P").get()
        docRef.await()
        if (docRef.isSuccessful) {
            Log.d("ref", "getPopularList: ${docRef.result}")
            docRef.result!!.forEach {
                seriesList.add(it.toObject())
            }
            Log.d("ref", "getPopular: $seriesList")

        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }
        return seriesList
    }

    suspend fun getNewSeriesList(): ArrayList<SeriesModel> {
        val seriesList = ArrayList<SeriesModel>()
        val docRef = fireStore.collection("Movie").whereEqualTo("is_series", true).whereEqualTo("status_code", "N").get()
        docRef.await()
        if (docRef.isSuccessful) {
            Log.d("ref", "getNewList: ${docRef.result}")
            docRef.result!!.forEach {
                seriesList.add(it.toObject())
            }
            Log.d("ref", "getNewList: $seriesList")
        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }
        return seriesList
    }
}