package com.ptkako.nv.novusvision.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ptkako.nv.novusvision.utility.runInIO
import com.ptkako.nv.novusvision.utility.runInMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val fireAuth: FirebaseAuth, private val fireStore: FirebaseFirestore) {

     fun registerUser(email: String, password: String): LiveData<Any> {
        val result = MutableLiveData<Any>()

        runInIO {
            fireAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        runInMain {
                            result.postValue(fireAuth.currentUser)
                        }
                    } else {
                        runInMain {
                            result.postValue(it.exception)
                        }
                    }
                }
        }
        return result
    }
}