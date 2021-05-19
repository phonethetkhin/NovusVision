package com.ptkako.nv.novusvision.viewmodel

import androidx.lifecycle.ViewModel
import com.ptkako.nv.novusvision.repository.AuthRepository

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    fun registerUser(email: String, password: String) = repository.registerUser(email, password)
}