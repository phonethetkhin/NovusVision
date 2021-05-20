package com.ptkako.nv.novusvision.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.ptkako.nv.novusvision.utility.runOnIO
import com.ptkako.nv.novusvision.utility.runOnMain
import kotlinx.coroutines.delay

class AuthRepository(private val fireAuth: FirebaseAuth, private val fireStore: FirebaseFirestore) {

    fun registerUser(email: String, password: String): LiveData<Any> {
        val result = MutableLiveData<Any>()
        runOnIO {
            delay(3000)
            fireAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        runOnMain {
                            result.postValue(fireAuth.currentUser)
                        }
                    } else {
                        runOnMain {
                            result.postValue(it.exception)
                        }
                    }
                }
        }
        return result
    }

    fun sendEmailVerification(currentUser: FirebaseUser): LiveData<Any> {
        val result = MutableLiveData<Any>()
        runOnIO {
            currentUser.sendEmailVerification().addOnCompleteListener {

                if (it.isSuccessful)
                    runOnMain { result.postValue("Success") }
                else
                    runOnMain { result.postValue(it.exception) }
            }
        }
        return result
    }

    fun uploadUserData(user: HashMap<String, out Any>): LiveData<Any> {
        val result = MutableLiveData<Any>()
        runOnIO {
            fireStore.collection("User")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    runOnMain { result.postValue(documentReference.id) }
                }
                .addOnFailureListener { e ->
                    runOnMain { result.postValue(e) }
                }
        }
        return result
    }


}