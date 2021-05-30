package com.ptkako.nv.novusvision.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ptkako.nv.novusvision.utility.runOnIO
import com.ptkako.nv.novusvision.utility.runOnMain
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class AuthRepository(private val fireAuth: FirebaseAuth, private val fireStore: FirebaseFirestore) {
    val registerUserLiveData = MutableLiveData<Any>()

    suspend fun userLogin(email: String, password: String): Any {
        delay(3000)
        return try {
            val result = fireAuth.signInWithEmailAndPassword(email, password)
            result.await()
            if (result.isSuccessful) currentUser()!! else result.exception!!.localizedMessage
        } catch (e: Exception) {
            e.localizedMessage!!
        }
    }

    fun registerUser(user: HashMap<String, String>) {
        runOnIO {
            try {
                val result = fireAuth.createUserWithEmailAndPassword(user["email"] as String, user["password"] as String)
                result.await()
                if (result.isSuccessful) {
                    user["user_id"] = currentUser()!!.uid
                    sendEmailVerification(user)
                } else {
                    runOnMain {
                        registerUserLiveData.postValue(result.exception)
                    }
                }
            } catch (e: Exception) {
                runOnMain {
                    registerUserLiveData.postValue(e)
                }
            }
        }
    }

    suspend fun sendEmailVerification(user: HashMap<String, String>? = null) {
        try {
            val result = currentUser()!!.sendEmailVerification()
            result.await()
            if (result.isSuccessful)
                if (user != null)
                    uploadUserData(user)
                else
                    runOnMain { registerUserLiveData.postValue("Send successfully.") }
            else
                runOnMain { registerUserLiveData.postValue(result.exception) }
        } catch (e: Exception) {
            runOnMain {
                registerUserLiveData.postValue(e)
            }
        }
    }

    private fun currentUser() = fireAuth.currentUser

    private suspend fun uploadUserData(user: HashMap<String, String>) {
        try {
            val result = fireStore.collection("User").add(user)
            result.await()
            if (result.isSuccessful)
                runOnMain { registerUserLiveData.postValue(result.result!!.id) }
            else
                runOnMain { registerUserLiveData.postValue(result.exception) }
        } catch (e: Exception) {
            runOnMain {
                registerUserLiveData.postValue(e)
            }
        }
    }

    suspend fun updateEmail(email: String): String {
        return try {
            val result = currentUser()!!.updateEmail(email)
            result.await()
            if (result.isSuccessful) "Update Successfully."
            else result.exception!!.localizedMessage
        } catch (e: Exception) {
            e.localizedMessage!!
        }
    }

    suspend fun reloadCurrentUser(): Any {
        return try {
            val reloadUser = currentUser()!!.reload()
            reloadUser.await()
            if (reloadUser.isSuccessful) currentUser()!!
            else reloadUser.exception!!.localizedMessage
        } catch (e: Exception) {
            e.localizedMessage!!
        }
    }

}