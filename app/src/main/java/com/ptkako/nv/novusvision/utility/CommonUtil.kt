package com.ptkako.nv.novusvision.utility

import android.annotation.SuppressLint
import android.util.Patterns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

fun getDate(): String {
    val c = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    return dateFormat.format(c.time)
}

@SuppressLint("SimpleDateFormat")
fun getDateString(
    stringDate: String,
    stringDateFormat: String,
    returnDateFormat: String
): String {
    return try {
        val date = SimpleDateFormat(stringDateFormat).parse(stringDate)
        SimpleDateFormat(returnDateFormat).format(date).replace("a.m.", "AM")
            .replace("am", "AM").replace("p.m.", "PM").replace("pm", "PM")
    } catch (e: ParseException) {
        e.message.toString()
    }
}