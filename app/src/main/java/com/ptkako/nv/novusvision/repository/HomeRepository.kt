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

    private fun getDocRef(collectionPath: String, statusCode: String?, isSeries: Boolean) =
        if (statusCode == null) fireStore.collection(collectionPath).whereEqualTo("is_series", isSeries).get()
        else fireStore.collection(collectionPath).whereEqualTo("is_series", isSeries).whereEqualTo("status_code", statusCode).get()


    //home fragment
    suspend fun getMovieListForHome(statusCode: String?): ArrayList<MovieModel> {
        val movieList = ArrayList<MovieModel>()
        val docRef = getDocRef("Movie", statusCode, false)
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

    //movies------------------------------------------------------------------------------------//
    suspend fun getMovieList(statusCode: String?): ArrayList<MovieModel> {
        val movieList = ArrayList<MovieModel>()
        val docRef = getDocRef("Movie", statusCode, false)
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

    //series--------------------------------------------------------------------------------------//
    suspend fun getSeriesList(statusCode: String?): ArrayList<SeriesModel> {
        val seriesList = ArrayList<SeriesModel>()
        val docRef = getDocRef("Movie", statusCode, true)
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