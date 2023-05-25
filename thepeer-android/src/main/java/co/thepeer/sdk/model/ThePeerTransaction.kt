package co.thepeer.sdk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class ThepeerEvent(
    val event: String
) : Parcelable