package co.thepeer.sdk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class ThepeerEvent(
    val event: String,
    val data: ThepeerTransaction
) : Parcelable

@Parcelize
data class ThepeerTransaction(
    val id: String,
    val remark: String,
    val amount: BigDecimal,
    val currency: String,
    val type: String,
    val refund: Boolean,
    val channel: String,
    val status: String,
    val checkout: ThepeerCheckout?,
    val mode: String,
    val reference: String,
    val user: ThepeerUser

) : Parcelable

@Parcelize
data class ThepeerUser(
    val name: String,
    val identifier: String,
    val identifier_type: String,
    val email: String?,
    val reference: String?
) : Parcelable

@Parcelize
data class ThepeerBusiness(
    val name: String,
    val logo: String
) : Parcelable

@Parcelize
data class ThepeerCheckout(
    val id: String,
    val amount: BigDecimal,
    val currency: String,
    val status: String,
    val linked_account: LinkedAccount

) : Parcelable

@Parcelize
data class LinkedAccount(
    val user: ThepeerUser,
    val business: ThepeerBusiness
) : Parcelable
