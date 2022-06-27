package co.thepeer.sdk

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import co.thepeer.sdk.model.ThepeerParam
import co.thepeer.sdk.model.ThepeerResult
import co.thepeer.sdk.model.ThepeerSDKType
import co.thepeer.sdk.ui.ThepeerResultContract
import co.thepeer.sdk.ui.ThepeerResultListener
import co.thepeer.sdk.utils.ThepeerConstants
import java.math.BigDecimal

/**
 * This is Thepeer SDK class instance
 * @param activity -> Required to launch the drop in UI activity
 * @param publicKey -> Required to authenticate the merchant
 * @param resultRegistry -> used to register the activity for result
 * @param amount -> The amount for the transaction
 * @param currency -> customer preferred currency
 * @param userReference -> Customer indexed user reference from ThePeer
 * @param meta -> Optional information for the transaction
 */
class Thepeer internal constructor(
    private var publicKey: String,
    private var resultRegistry: ActivityResultLauncher<ThepeerParam>,
    private var activity: AppCompatActivity,
    private var amount: BigDecimal,
    private var currency: String,
    private var userReference: String,
    private var meta: Map<String, String> = emptyMap(),
) {

    /**
     * Config builder for initialisation parameters
     * @param activity -> Required to launch the drop in UI activity
     * @param amount -> The amount for the transaction
     * @param currency -> customer preferred currency
     * @param userReference -> Customer indexed user reference from ThePeer
     * @param resultListener -> result event callback
     */
    class Builder(
        private val activity: AppCompatActivity,
        private var amount: BigDecimal,
        private var currency: String,
        private var userReference: String,
        resultListener: ThepeerResultListener
    ) {

        var resultRegistry = activity.registerForActivityResult(
            ThepeerResultContract(),
            activity.activityResultRegistry
        ) { chargeResult ->
            when (chargeResult) {
                is ThepeerResult.Success -> resultListener.onSuccess(chargeResult.transaction)
                is ThepeerResult.Error -> resultListener.onError(chargeResult.error)
                ThepeerResult.Cancelled -> resultListener.onCancelled()
            }
        }
        private var publicKey = getPublicKeyFromManifest(activity)

        private var meta: Map<String, String> = emptyMap()

        private fun getPublicKeyFromManifest(context: Context): String {
            val applicationInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            return applicationInfo.metaData?.getString(ThepeerConstants.PUBLIC_KEY_FROM_MANIFEST)
                .orEmpty()
        }

        fun setAmount(amount: BigDecimal): Builder {
            this.amount = amount
            return this
        }


        fun setCurrency(currency: String): Builder {
            this.currency = currency
            return this
        }

        fun setUserReference(userReference: String): Builder {
            this.userReference = userReference
            return this
        }

        fun setMeta(meta: Map<String, String>): Builder {
            this.meta = meta
            return this
        }

        fun build(): Thepeer {
            return Thepeer(
                publicKey,
                resultRegistry,
                activity,
                amount,
                currency,
                userReference,
                meta
            )

        }
    }


    /**
     * This function will be called to launch ThePeer Send Money Widget
     */
    fun send() {
        val params = ThepeerParam(
            publicKey,
            getSdkType(ThepeerSDKType.SEND),
            amount,
            currency,
            userReference,
            null,
            meta
        )

        if (publicKey.isEmpty()) error("Add publicKey from business dashboard as meta data to manifest file. Check documentation")

        resultRegistry.launch(params)
    }

    /**
     * This function will be called to launch ThePeer Checkout Widget
     */
    fun checkout(emailAddress: String) {
        val params = ThepeerParam(
            publicKey,
            getSdkType(ThepeerSDKType.CHECKOUT),
            amount,
            currency,
            userReference,
            emailAddress,
            meta
        )

        if (publicKey.isEmpty()) error("Add publicKey from business dashboard as meta data to manifest file. Check documentation")

        resultRegistry.launch(params)
    }

    /**
     * This function will be called to launch ThePeer Direct Charge Widget
     */
    fun directCharge() {
        val params = ThepeerParam(
            publicKey,
            getSdkType(ThepeerSDKType.DIRECT_CHARGE),
            amount,
            currency,
            userReference,
            null,
            meta
        )

        if (publicKey.isEmpty()) error("Add publicKey from business dashboard as meta data to manifest file. Check documentation")

        resultRegistry.launch(params)
    }


    private fun getSdkType(type: Enum<ThepeerSDKType>): String {
        return when (type) {
            ThepeerSDKType.SEND -> {
                return ThepeerConstants.SEND
            }
            ThepeerSDKType.CHECKOUT -> {
                return ThepeerConstants.CHECKOUT
            }
            ThepeerSDKType.DIRECT_CHARGE -> {
                return ThepeerConstants.DIRECT_CHARGE
            }
            else -> ""
        }

    }
}