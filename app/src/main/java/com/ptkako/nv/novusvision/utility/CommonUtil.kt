package com.ptkako.nv.novusvision.utility

import android.util.Patterns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun runOnMain(work: suspend () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        work()
    }
}

fun runOnIO(work: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        work()
    }
}

fun isEmailDataInvalid(email: String) = !Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun convertMMSSFormat(second: Long) =
    String.format("%02d:%02d", (second % 3600) / 60, (second % 60))