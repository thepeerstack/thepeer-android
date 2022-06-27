package co.thepeer.sdk.utils

import android.util.Log
import android.webkit.JavascriptInterface
import co.thepeer.sdk.model.ThepeerResult
import co.thepeer.sdk.model.ThepeerEvent
import com.google.gson.Gson

/**
 * This webinterface handles events from the webview
 */
internal class WebInterface(private val redirect: (ThepeerResult) -> Unit ) {


    private val SEND_INSUFFICIENT_FUNDS = "send.insufficient_funds"
    private val SEND_USER_INSUFFICIENT_FUNDS = "send.user_insufficient_funds"
    private val SEND_SUCCESS = "send.success"
    private val DIRECT_CHARGE_INSUFFICIENT_FUNDS = "direct_debit.insufficient_funds"
    private val DIRECT_CHARGE_USER_INSUFFICIENT_FUNDS =
        "direct_debit.user_insufficient_funds"
    private val DIRECT_CHARGE_SUCCESS = "direct_debit.success"
    private val DIRECT_CHARGE_BUSINESS_DECLINE = "direct_debit.business_decline"
    private val DIRECT_CHARGE_USER_DECLINE = "direct_debit.user_decline"
    private val CHECKOUT_INSUFFICIENT_FUNDS = "checkout.insufficient_funds"
    private val CHECKOUT_USER_INSUFFICIENT_FUNDS = "checkout.user_insufficient_funds"
    private val CHECKOUT_SUCCESS = "checkout.success"
    private val CHECKOUT_BUSINESS_DECLINE = "checkout.business_decline"
    private val CHECKOUT_USER_DECLINE = "checkout.user_decline"
    private val SEND_CLOSE = "send.close"
    private val DIRECT_CHARGE_CLOSE = "direct_debit.close"
    private val CHECKOUT_CLOSE = "checkout.close"


     inline fun <reified T> convertToGsonFromString(json: String): T {
        val adapter = Gson().getAdapter(T::class.java)
        return adapter.fromJson(json)
    }


    fun handleSendEvent(event:  ThepeerEvent) {
        when (event.event) {
            SEND_SUCCESS -> {
                redirect(ThepeerResult.Success(event.data))
            }
            SEND_CLOSE -> {
                redirect(ThepeerResult.Cancelled)
            }
            else -> {
                redirect(ThepeerResult.Error(Throwable(event.event.getLastPart())))
            }
        }

    }

     fun handleCheckoutEvent(event:  ThepeerEvent) {
        when (event.event) {
            CHECKOUT_SUCCESS -> {
                redirect(ThepeerResult.Success(event.data))
            }
            CHECKOUT_CLOSE -> {
                redirect(ThepeerResult.Cancelled)
            }
            else -> {
                redirect(ThepeerResult.Error(Throwable(event.event.getLastPart())))
            }
        }

    }

    fun handleDirectDebitEvent(event:  ThepeerEvent) {

        when (event.event) {
            DIRECT_CHARGE_SUCCESS -> {
                redirect(ThepeerResult.Success(event.data))
            }
            DIRECT_CHARGE_CLOSE -> {
                redirect(ThepeerResult.Cancelled)
            }
            else -> {
                redirect(ThepeerResult.Error(Throwable(event.event.getLastPart())))
            }
        }

    }



    @JavascriptInterface
    fun sendResponse(response: String) {
        Logger.log(this, response)
        try {
            val event: ThepeerEvent = convertToGsonFromString(response)
            when (event.event.getFirstPart()) {
                "send" -> {
                    handleSendEvent(event)
                }
                "checkout" -> {
                    handleCheckoutEvent(event)
                }
                "direct_debit" -> {
                    handleDirectDebitEvent(event)
                }
            }
        } catch (e: Exception) {
            Log.e("WebInterface", e.message.toString())

        }


    }
}