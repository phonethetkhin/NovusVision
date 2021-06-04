package com.ptkako.nv.novusvision.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.di.instance

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


@Suppress("DEPRECATION")
fun Context.isInternetAvailable(): Boolean {
    var result = false
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
        cm.getNetworkCapabilities(cm.activeNetwork)?.apply {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
    } else {
        result = cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
    return result
}

inline fun <reified VM : ViewModel, T> T.kodeinViewModel(): Lazy<VM> where T : DIAware, T : AppCompatActivity {
    return lazy { ViewModelProvider(this, direct.instance()).get(VM::class.java) }
}

inline fun <reified VM : ViewModel, T> T.kodeinViewModel(): Lazy<VM> where T : DIAware, T : Fragment {
    return lazy { ViewModelProvider(requireActivity(), direct.instance()).get(VM::class.java) }
}