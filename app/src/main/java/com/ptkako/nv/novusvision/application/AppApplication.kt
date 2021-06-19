package com.ptkako.nv.novusvision.application

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ptkako.nv.novusvision.dialog.ProgressDialogFragment
import com.ptkako.nv.novusvision.repository.*
import com.ptkako.nv.novusvision.viewmodel.*
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class AppApplication : Application(), DIAware {
    override val di by DI.lazy {
        import(androidXModule(this@AppApplication))

        bindSingleton { Firebase.firestore }
        bindSingleton { Firebase.auth }

        bind<ProgressDialogFragment>() with provider { ProgressDialogFragment() }

        bindSingleton { ViewModelFactory(di.direct) }

        bindSingleton { AuthRepository(instance(), instance()) }
        bindSingleton { HomeRepository(instance(), instance()) }
        bindSingleton { SeriesDetailRepository(instance(), instance()) }
        bindSingleton { MovieDetailRepository(instance(), instance()) }
        bindSingleton { PlayListRepository(instance(), instance()) }
        bindSingleton { HistoryRepository(instance(), instance()) }

        bind<AuthViewModel>(AuthViewModel::class.java.simpleName) with provider { AuthViewModel(instance()) }
        bind<HomeViewModel>(HomeViewModel::class.java.simpleName) with provider { HomeViewModel(instance()) }
        bind<SeriesDetailViewModel>(SeriesDetailViewModel::class.java.simpleName) with provider { SeriesDetailViewModel(instance()) }
        bind<MovieDetailViewModel>(MovieDetailViewModel::class.java.simpleName) with provider { MovieDetailViewModel(instance()) }
        bind<EntireListViewModel>(EntireListViewModel::class.java.simpleName) with provider { EntireListViewModel(instance()) }
        bind<PlayListViewModel>(PlayListViewModel::class.java.simpleName) with provider { PlayListViewModel(instance()) }
        bind<HistoryViewModel>(HistoryViewModel::class.java.simpleName) with provider { HistoryViewModel(instance()) }


    }
}