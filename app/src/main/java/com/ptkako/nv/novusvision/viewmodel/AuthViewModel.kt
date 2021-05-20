package com.ptkako.nv.novusvision.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.ptkako.nv.novusvision.repository.AuthRepository

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    fun registerUser(email: String, password: String) = repository.registerUser(email, password)

    fun sendEmailVerification(currentUser: FirebaseUser) = repository.sendEmailVerification(currentUser)

    fun uploadUserData(user: HashMap<String, out Any>) = repository.uploadUserData(user)
}