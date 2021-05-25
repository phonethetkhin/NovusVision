package com.ptkako.nv.novusvision.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ptkako.nv.novusvision.utility.runOnIO
import com.ptkako.nv.novusvision.utility.runOnMain
import kotlinx.coroutines.tasks.await

class AuthRepository(private val fireAuth: FirebaseAuth, private val fireStore: FirebaseFirestore) {
    val registerUserLiveData = MutableLiveData<Any>()

    fun registerUser(user: HashMap<String, String>) {
        runOnIO {
            val result = fireAuth.createUserWithEmailAndPassword(user["email"] as String, user["password"] as String)
            result.await()
            if (result.isSuccessful) {
                user["user_id"] = fireAuth.currentUser!!.uid
                sendEmailVerification(user)
            } else {
                runOnMain {
                    registerUserLiveData.postValue(result.exception)
                }
            }
        }
    }

    suspend fun sendEmailVerification(user: HashMap<String, String>? = null) {

        val result = fireAuth.currentUser!!.sendEmailVerification()
        result.await()
        if (result.isSuccessful)
            if (user != null)
                uploadUserData(user)
            else
                runOnMain { registerUserLiveData.postValue("Send successfully.") }
        else
            runOnMain { registerUserLiveData.postValue(result.exception) }
    }

    private suspend fun uploadUserData(user: HashMap<String, String>) {

        val result = fireStore.collection("User").add(user)
        result.await()
        if (result.isSuccessful)
            runOnMain { registerUserLiveData.postValue(result.result!!.id) }
        else
            runOnMain { registerUserLiveData.postValue(result.exception) }
    }

}