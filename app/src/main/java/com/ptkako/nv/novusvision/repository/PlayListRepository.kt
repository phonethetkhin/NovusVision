@file:Suppress("UNCHECKED_CAST")

package com.ptkako.nv.novusvision.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.model.PlaylistModel
import com.ptkako.nv.novusvision.utility.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PlayListRepository(private val context: Context, private val fireStore: FirebaseFirestore) {
    suspend fun getPlaylistByUser(userId: String): ArrayList<MovieModel> {
        Log.d("playlistSequence", "1")
        val playlist = ArrayList<MovieModel>()
        Log.d("playlistSequence", "2")

        val docRef = fireStore.collection("Playlist").whereEqualTo("user_id", userId).get()
        docRef.await()
        Log.d("playlistSequence", "3")

        if (docRef.isSuccessful) {
            Log.d("playlistSequence", "4")

            docRef.result!!.forEach {
                Log.d("playlistSequence", "5, ${it.toObject<PlaylistModel>().movie_id}")

                val movie = withContext(Dispatchers.IO) { getMovieByMovieId(it.toObject<PlaylistModel>().movie_id) }
                Log.d("playlistSequence", "10")

                if (movie != null) {
                    playlist.add(movie)
                }
                Log.d("playlistSequence", "11 ${playlist.size}")
            }
            Log.d("playlistSequence","12")

        } else {
            context.showToast(docRef.exception!!.localizedMessage)
        }
        Log.d("playlistSequence","13")

        return playlist
    }

    suspend fun getMovieByMovieId(movieId: String): MovieModel? {
        Log.d("playlistSequence", "6")

        var movieModel: MovieModel? = null
        val docRef = fireStore.collection("Movie").document(movieId).get()
        docRef.await()
        Log.d("playlistSequence", "7")

        if (docRef.isSuccessful) {
            Log.d("playlistSequence", "8")

            movieModel = docRef.result!!.toObject<MovieModel>()
        } else {
            Log.d("playlistSequence", "8else")

            context.showToast(docRef.exception!!.localizedMessage)
        }
        Log.d("playlistSequence", "9")

        return movieModel
    }
}