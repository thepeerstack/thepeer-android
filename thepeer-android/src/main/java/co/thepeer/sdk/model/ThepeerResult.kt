package co.thepeer.sdk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ThepeerResult : Parcelable {
    @Parcelize
    data class Success(val transaction: ThepeerTransaction) : ThepeerResult()

    @Parcelize
    data class Error(val error: Throwable) : ThepeerResult()

    @Parcelize
    object Cancelled : ThepeerResult()
}
