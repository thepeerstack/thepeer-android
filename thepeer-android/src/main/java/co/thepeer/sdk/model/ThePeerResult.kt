package co.thepeer.sdk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ThePeerResult: Parcelable {
    @Parcelize
    data class Success(val transaction: ThePeerTransaction) : ThePeerResult()

    @Parcelize
    data class Error(val error: Throwable) : ThePeerResult()

    @Parcelize
    object  Cancelled : ThePeerResult()
}