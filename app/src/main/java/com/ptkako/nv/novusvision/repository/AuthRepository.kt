package com.ptkako.nv.novusvision.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.ptkako.nv.novusvision.utility.runOnIO
import com.ptkako.nv.novusvision.utility.runOnMain
import kotlinx.coroutines.delay

class AuthRepository(private val fireAuth: FirebaseAuth, private val fireStore: FirebaseFirestore) {
    val registerUserLiveData = MutableLiveData<Any>()

    fun registerUser(user: HashMap<String, String>) {
        runOnIO {
            fireAuth.createUserWithEmailAndPassword(user["email"] as String, user["password"] as String)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        user["user_id"] = fireAuth.currentUser!!.uid
                        sendEmailVerification(fireAuth.currentUser!!, user)
                    } else {
                        runOnMain {
                            registerUserLiveData.postValue(it.exception)
                        }
                    }
                }
        }
    }

    private fun sendEmailVerification(currentUser: FirebaseUser, user: HashMap<String, out Any>) {

        currentUser.sendEmailVerification().addOnCompleteListener {
            if (it.isSuccessful)
                uploadUserData(user)
            else
                runOnMain { registerUserLiveData.postValue(it.exception) }
        }
    }

    private fun uploadUserData(user: HashMap<String, out Any>) {

        fireStore.collection("User")
            .add(user)
            .addOnSuccessListener { documentReference ->
                runOnMain { registerUserLiveData.postValue(documentReference.id) }
            }
            .addOnFailureListener { e ->
                runOnMain { registerUserLiveData.postValue(e) }
            }
    }

}