@file:Suppress("UNCHECKED_CAST")

package com.ptkako.nv.novusvision.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ptkako.nv.novusvision.utility.runOnIO
import com.ptkako.nv.novusvision.utility.runOnMain
import com.ptkako.nv.novusvision.utility.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MovieDetailRepository(private val context: Context, private val fireStore: FirebaseFirestore) {

    suspend fun getMovieDocumentId(movieName: String): String {
        var documentId = ""
        val docRef = fireStore.collection("Movie").whereEqualTo("movie_name", movieName).get()
        docRef.await()
        if (docRef.isSuccessful) {
            val resultList = docRef.result!!.documents
            if (resultList.isNotEmpty()) {
                documentId = docRef.result!!.documents[0].reference.id
            }
        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }
        return documentId
    }

    suspend fun addPlaylist(playlist: HashMap<String, String>) {
        runOnIO {
            try {
                val result = fireStore.collection("Playlist").add(playlist)
                result.await()
                if (result.isSuccessful)
                    runOnMain { context.showToast("Added to Playlist") }
                else
                    runOnMain { context.showToast(result.exception!!.localizedMessage) }
            } catch (e: Exception) {
                runOnMain { context.showToast(e.localizedMessage) }
            }
        }
    }

   suspend fun checkExistingPlaylist(movieId: String, userId: String): Any? {
        var existing: Any? = null
            val result = fireStore.collection("Playlist").whereEqualTo("movie_id", movieId)
                .whereEqualTo("user_id", userId).get()
            result.await()
            if (result.isSuccessful) {
                val resultList = result.result!!.documents
                Log.d("resultlist", resultList.size.toString())
                if (resultList.isNotEmpty()) {
                    Log.d("resultlist", "Not Empty")
                    existing = result.result!!.documents[0].reference.id
                    Log.d("resultlist", "$existing")
                }
            } else {
                existing = result.exception!!
            }
        return existing
    }
}