package co.thepeer.sdk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ThepeerResult : Parcelable {
    @Parcelize
    data class Success(val response: String) : ThepeerResult()

    @Parcelize
    data class Error(val error: String) : ThepeerResult()

    @Parcelize
    object Cancelled : ThepeerResult()
}
