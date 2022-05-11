package co.thepeer.sdk.utils

import android.content.Intent
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import co.thepeer.sdk.model.ThePeerEvent
import co.thepeer.sdk.model.ThePeerResult
import com.google.gson.Gson

/**
 * This webinterface handles events from the webview
 */
internal class WebInterface(private val activity: FragmentActivity) {


    private val SEND_INSUFFICIENT_FUNDS = "send.insufficient_funds"
    private val SEND_USER_INSUFFICIENT_FUNDS = "send.user_insufficient_funds"
    val SEND_SUCCESS = "send.success"
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
    private val SEND_CLOSE = "sed.close"
    private val DIRECT_CHARGE_CLOSE = "direct_debit.close"
    private val CHECKOUT_CLOSE = "checkout.close"


    private inline fun <reified T> convertToGsonFromString(json: String): T?  =
        try{
            val adapter = Gson().getAdapter(T::class.java)
            adapter.fromJson(json)
        }catch (e: Exception){
            Log.e("WebInterface", e.message.toString())
            null
        }


    private fun handleSendEvent(event: ThePeerEvent) {
        when (event.event) {
            SEND_SUCCESS -> {
                redirectWithResult(ThePeerResult.Success(event.data))
            }
            SEND_CLOSE -> {
                redirectWithResult(ThePeerResult.Cancelled)
            }
            else -> {
                redirectWithResult(ThePeerResult.Error(Throwable(event.event.getLastPart())))
            }
        }

    }

    private fun handleCheckoutEvent(event: ThePeerEvent) {
        when (event.event) {
            CHECKOUT_SUCCESS -> {
                redirectWithResult(ThePeerResult.Success(event.data))
            }
            CHECKOUT_CLOSE -> {
                redirectWithResult(ThePeerResult.Cancelled)
            }
            else -> {
                redirectWithResult(ThePeerResult.Error(Throwable(event.event.getLastPart())))
            }
        }

    }

    private fun handleDirectChargeEvent(event: ThePeerEvent) {

        when (event.event) {
            DIRECT_CHARGE_SUCCESS -> {
                redirectWithResult(ThePeerResult.Success(event.data))
            }
            DIRECT_CHARGE_CLOSE -> {
                redirectWithResult(ThePeerResult.Cancelled)
            }
            else -> {
                redirectWithResult(ThePeerResult.Error(Throwable(event.event.getLastPart())))
            }
        }

    }

    private fun redirectWithResult(result: ThePeerResult) {
        val resultData = Intent()
        resultData.putExtra(ThePeerConstants.TRANSACTION_RESULT, result)
        activity.setResult(AppCompatActivity.RESULT_OK, resultData)
        activity.finish()
    }

    @JavascriptInterface
    fun sendResponse(response: String) {
        Logger.log(this, response)
        val event: ThePeerEvent? = convertToGsonFromString(response)
        when (event?.event?.getFirstPart()) {
            "send" -> {
                handleSendEvent(event)
            }
            "checkout" -> {
                handleCheckoutEvent(event)
            }
            "direct_debit" -> {
                handleDirectChargeEvent(event)
            }
        }
    }
}