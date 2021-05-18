package com.ptkako.nv.novusvision.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository(private val fireAuth: FirebaseAuth, private val fireStore: FirebaseFirestore) {

    fun registerUser(email: String, password: String): LiveData<Any> {
        val result = MutableLiveData<Any>()

        fireAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.postValue(fireAuth.currentUser)

                } else {
                    result.postValue(it.exception)
                }
            }

        return result
    }
}