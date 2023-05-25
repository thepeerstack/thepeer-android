package co.thepeer.sdk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

/**
 * These are resolved params from the user provided config to initiate a transaction
 */
@Parcelize
data class ThepeerParam(
    val publicKey: String,
    val sdkType: String,
    val amount: BigDecimal,
    val currency: String,
    val userReference: String,
    val emailAddress: String? = null,
    val meta: Map<String, String>,
) : Parcelable

enum class ThepeerSDKType { SEND, CHECKOUT, DIRECT_CHARGE, NONE }

/**
 * This is the public data class for getting user config for a transaction.
 */
data class ThepeerConfig(
    val amount: BigDecimal,
    val currency: String,
    val meta: Map<String, String> = emptyMap(),
)
