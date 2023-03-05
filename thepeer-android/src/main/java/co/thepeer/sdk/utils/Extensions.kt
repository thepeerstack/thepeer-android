package co.thepeer.sdk.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun String.getLastPart() = this.split(".")[1]
fun String.getFirstPart() = this.split(".")[0]

/**
 * [returns] true if the device is connected to the internet. False otherwise.
 */
val Context.isNetworkConnectionAvailable: Boolean
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
