package co.thepeer.sdk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal


@Parcelize
data class ThePeerEvent(
    val event: String,
    val data: ThePeerTransaction
) : Parcelable


@Parcelize
data class ThePeerTransaction(
    val id: String,
    val remark: String,
    val amount: BigDecimal,
    val currency: String,
    val type: String,
    val refund: Boolean,
    val channel: String,
    val status: String,
    val checkout: ThePeerCheckout?,
    val mode: String,
    val reference: String,
    val user: ThePeerUser

) : Parcelable


@Parcelize
data class ThePeerUser(
    val name: String,
    val identifier: String,
    val identifier_type: String,
    val email: String?,
    val reference: String?
) : Parcelable


@Parcelize
data class ThePeerBusiness(
    val name: String,
    val logo: String
) : Parcelable

@Parcelize
data class ThePeerCheckout(
    val id: String,
    val amount: BigDecimal,
    val currency: String,
    val status: String,
    val linked_account: LinkedAccount


) : Parcelable

@Parcelize
data class LinkedAccount(
    val user: ThePeerUser,
    val business: ThePeerBusiness
) : Parcelable