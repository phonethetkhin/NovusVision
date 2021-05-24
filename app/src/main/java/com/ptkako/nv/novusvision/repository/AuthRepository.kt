package com.ptkako.nv.novusvision.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ptkako.nv.novusvision.utility.runOnIO
import com.ptkako.nv.novusvision.utility.runOnMain

class AuthRepository(private val fireAuth: FirebaseAuth, private val fireStore: FirebaseFirestore) {
    val registerUserLiveData = MutableLiveData<Any>()

    fun registerUser(user: HashMap<String, String>) {
        runOnIO {
            fireAuth.createUserWithEmailAndPassword(user["email"] as String, user["password"] as String)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        user["user_id"] = fireAuth.currentUser!!.uid
                        sendEmailVerification(user)
                    } else {
                        runOnMain {
                            registerUserLiveData.postValue(it.exception)
                        }
                    }
                }
        }
    }

    private fun sendEmailVerification(user: HashMap<String, String>? = null) {

        fireAuth.currentUser!!.sendEmailVerification().addOnCompleteListener {
            if (it.isSuccessful)
                if (user != null)
                    uploadUserData(user)
                else
                    registerUserLiveData.postValue("Success resend email")
            else
                runOnMain { registerUserLiveData.postValue(it.exception) }
        }
    }

    private fun uploadUserData(user: HashMap<String, String>) {

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