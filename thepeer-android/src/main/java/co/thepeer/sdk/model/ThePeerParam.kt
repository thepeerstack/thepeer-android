package co.thepeer.sdk.model

import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.math.BigDecimal

/**
 * details required to initiate a transaction
 */
@Parcelize
data class ThePeerParam(
    val publicKey: String,
    val sdkType: String,
    val amount: BigDecimal,
    val currency: String,
    val userReference: String,
    val emailAddress: String? = null,
    val meta: Map<String, String>,
): Parcelable


enum class ThePeerSdkType{SEND_MONEY, CHECKOUT, DIRECT_CHARGE, NONE}