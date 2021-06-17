package com.ptkako.nv.novusvision.utility

import android.content.Context

/**
 * This function is about setting the string pref.
 *
 * @param context (Context)
 * @param name (String)
 * @param key (String)
 * @param value (String)
 */
fun setStringPref(context: Context, name: String, key: String, value: String) {
    val pref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(key, value)
    editor.apply()
}

/**
 * This function is about getting the string pref.
 *
 * @param context (Context)
 * @param name (String)
 * @param key (String)
 * @param defaultValue (String)
 * @return String
 */
fun getStringPref(context: Context, name: String, key: String, defaultValue: String): String? {
    val pref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    return pref.getString(key, defaultValue)
}