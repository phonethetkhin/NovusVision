package com.ptkako.nv.novusvision.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.ptkako.nv.novusvision.model.ContentModel
import com.ptkako.nv.novusvision.model.MovieInfoModel
import com.ptkako.nv.novusvision.model.MoviesModel
import kotlinx.coroutines.tasks.await

class HomeRepository(private val fireStore: FirebaseFirestore) {
    val contentLiveData = MutableLiveData<Any>()

    suspend fun getMovieList(): ArrayList<MoviesModel> {
        Log.d("stepbystep", "1")

        val movieList = ArrayList<MoviesModel>()
        Log.d("stepbystep", "2")

        Log.d("lifecycle", "getMovieListRepo")
        val docRef = fireStore.collection("Movie")
        Log.d("stepbystep", "3")

        docRef.get().addOnSuccessListener { documents ->
            Log.d("stepbystep", "4")

            for (document in documents) {
                Log.d("stepbystep", "5")

                val movie = document.toObject<MoviesModel>()
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

    suspend fun getMovieInfo(movieInfoId: Int): MovieInfoModel? {
        var movieInfoModel: MovieInfoModel? = null

        val docRef = fireStore.collection("Movie_Info").document(movieInfoId.toString())
        docRef.get().addOnSuccessListener { document ->
            movieInfoModel = document.toObject<MovieInfoModel>()
        }
            .addOnFailureListener { exception ->

            }.await()
        return movieInfoModel
    }

    suspend fun getContent(contentId: Int): ContentModel? {
        var contentModel: ContentModel? = null
        val docRef = fireStore.collection("Content").document(contentId.toString())
        docRef.get().addOnSuccessListener { document ->
            contentModel = document.toObject<ContentModel>()
        }
            .addOnFailureListener { exception ->

            }.await()
        return contentModel
    }
}