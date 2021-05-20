package com.ptkako.nv.novusvision.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun runOnMain(work: () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        work()
    }
}

fun runOnIO(work: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        work()
    }
}