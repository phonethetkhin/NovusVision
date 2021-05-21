package com.ptkako.nv.novusvision.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.ptkako.nv.novusvision.model.ContentModel
import com.ptkako.nv.novusvision.model.MovieInfoModel
import com.ptkako.nv.novusvision.model.MoviesModel
import com.ptkako.nv.novusvision.utility.runOnIO
import com.ptkako.nv.novusvision.utility.runOnMain

class HomeRepository(private val fireStore: FirebaseFirestore) {
    val movieListLiveData = MutableLiveData<Any>()
    val movieInfoLiveData = MutableLiveData<Any>()
    val contentLiveData = MutableLiveData<Any>()

    suspend fun getMovieList() {
        runOnIO {
            val movieList = ArrayList<MoviesModel>()
            val docRef = fireStore.collection("Movie")
            docRef.get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val movie = document.toObject<MoviesModel>()
                    movieList.add(movie)
                }
                runOnMain {
                    movieListLiveData.postValue(movieList)
                }
            }
                .addOnFailureListener { exception ->
                    runOnMain { movieListLiveData.postValue(exception) }
                }
        }
    }

    suspend fun getMovieInfo(movieInfoId: Int) {
        runOnMain {
            val docRef = fireStore.collection("Movie_Info").document(movieInfoId.toString())
            docRef.get().addOnSuccessListener { document ->
                movieInfoLiveData.postValue(document.toObject<MovieInfoModel>())
            }
                .addOnFailureListener { exception ->
                    movieInfoLiveData.postValue(exception)
                }
        }
    }

    suspend fun getContent(contentId: Int) {
        runOnMain {
            val docRef = fireStore.collection("Content").document(contentId.toString())
            docRef.get().addOnSuccessListener { document ->
                contentLiveData.postValue(document.toObject<ContentModel>())
            }
                .addOnFailureListener { exception ->
                    contentLiveData.postValue(exception)
                }
        }
    }
}