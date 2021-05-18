package com.ptkako.nv.novusvision.application

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ptkako.nv.novusvision.repository.AuthRepository
import com.ptkako.nv.novusvision.utility.bindViewModel
import com.ptkako.nv.novusvision.viewmodel.AuthViewModel
import com.ptkako.nv.novusvision.viewmodel.ViewModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class AppApplication : Application(), DIAware {
    override val di by DI.lazy {
        import(androidXModule(this@AppApplication))

        bind() from singleton { Firebase.firestore }
        bind() from singleton { Firebase.auth }

        bind() from singleton { ViewModelFactory(di.direct) }

        bind() from singleton { AuthRepository(instance(), instance()) }
        bindViewModel<AuthViewModel>() with provider { AuthViewModel(instance()) }


    }
}