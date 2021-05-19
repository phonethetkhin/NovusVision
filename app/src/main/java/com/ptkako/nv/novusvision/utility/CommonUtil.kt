package com.ptkako.nv.novusvision.utility

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun runInMain(work: () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        work()
    }
}

fun runInIO(work: () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        work()
    }
}