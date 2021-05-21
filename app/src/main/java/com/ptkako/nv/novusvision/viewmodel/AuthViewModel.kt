package com.ptkako.nv.novusvision.viewmodel

import androidx.lifecycle.ViewModel
import com.ptkako.nv.novusvision.repository.AuthRepository

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    val registerUserLiveData = repository.registerUserLiveData

    fun registerUser(user: HashMap<String, String>) = repository.registerUser(user)
}