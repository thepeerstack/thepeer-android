package co.thepeer.sdk.utils

import co.thepeer.sdk.model.ThepeerParam
import com.google.gson.Gson

internal object Urls {

    fun createTransactionUrl(data: ThepeerParam): String {
        var BASE_URL = "https://groot.thepeer.co?"

        val params = mapOf(
            "publicKey" to data.publicKey,
            "amount" to data.amount,
            "currency" to data.currency,
            "userReference" to data.userReference,
            "email" to data.emailAddress,
            "sdkType" to data.sdkType,
            "meta" to Gson().toJson(data.meta)
        )

        for (k in params.keys) {
            if (params[k] != null) {
                val value = params[k]
                BASE_URL = "$BASE_URL$k=$value&"
            }
        }
        return BASE_URL.dropLast(1)
    }
}
