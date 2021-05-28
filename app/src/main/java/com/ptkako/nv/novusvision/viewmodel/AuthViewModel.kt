package com.ptkako.nv.novusvision.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptkako.nv.novusvision.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    val registerUserLiveData = repository.registerUserLiveData
    val handleBtnResentLiveData = MutableLiveData<Boolean>()
    val handleBtnTextLiveData = MutableLiveData<String>()

    fun registerUser(user: HashMap<String, String>) = repository.registerUser(user)

    fun resendEmailVerification() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.sendEmailVerification()
            }
        }
    }
}