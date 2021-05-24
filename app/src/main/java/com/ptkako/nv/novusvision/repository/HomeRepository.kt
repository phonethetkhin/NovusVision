package com.ptkako.nv.novusvision.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.model.SeriesModel
import kotlinx.coroutines.tasks.await

class HomeRepository(private val fireStore: FirebaseFirestore) {

    suspend fun getMovieList(): ArrayList<MovieModel> {
        Log.d("stepbystep", "1")

        val movieList = ArrayList<MovieModel>()
        Log.d("stepbystep", "2")

        Log.d("lifecycle", "getMovieListRepo")
        val docRef = fireStore.collection("Movie").whereEqualTo("is_series", false)
        Log.d("stepbystep", "3")

        docRef.get().addOnSuccessListener { documents ->
            Log.d("stepbystep", "4")
            for (document in documents) {
                Log.d("stepbystep", "5")

                val movie = document.toObject<MovieModel>()
                movieList.add(movie)
                Log.d("stepbystep", "6")

            }
            Log.d("stepbystep", "7")

        }
            .addOnFailureListener { exception ->
            }.await()
        Log.d("stepbystep", "8")

        Log.d("lifecycle", movieList.toString())

        return movieList
    }

    suspend fun getSeriesList(): ArrayList<SeriesModel> {
        Log.d("stepbystep", "1")

        val seriesList = ArrayList<SeriesModel>()
        Log.d("stepbystep", "2")

        Log.d("lifecycle", "getMovieListRepo")
        val docRef = fireStore.collection("Movie").whereEqualTo("is_series", true)
        Log.d("stepbystep", "3")

        docRef.get().addOnSuccessListener { documents ->
            Log.d("stepbystep", "4")
            for (document in documents) {
                Log.d("stepbystep", "5")

                val series = document.toObject<SeriesModel>()
                seriesList.add(series)
                Log.d("stepbystep", "6")

            }
            Log.d("stepbystep", "7")

        }
            .addOnFailureListener { exception ->
            }.await()
        Log.d("stepbystep", "8")

        Log.d("lifecycle", seriesList.toString())

        return seriesList
    }
}