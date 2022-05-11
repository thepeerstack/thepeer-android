package co.thepeer.sdk

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import co.thepeer.sdk.model.ThePeerParam
import co.thepeer.sdk.model.ThePeerResult
import co.thepeer.sdk.model.ThePeerSdkType
import co.thepeer.sdk.ui.ThePeerResultContract
import co.thepeer.sdk.ui.ThePeerResultListener
import co.thepeer.sdk.utils.ThePeerConstants
import java.math.BigDecimal

/**
 * This is ThePeer SDk class instance
 * @param activity -> Required to launch the drop in UI activity
 * @param publicKey -> Required to authenticate the merchant
 * @param resultRegistry -> used to register the activity for result
 * @param amount -> The amount for the transaction
 * @param currency -> customer preferred currency
 * @param userReference -> Customer indexed user reference from ThePeer
 * @param meta -> Optional information for the transaction
 */
class ThePeer internal constructor(
    private var publicKey: String,
    private var resultRegistry: ActivityResultLauncher<ThePeerParam>,
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
        resultListener: ThePeerResultListener
    ) {

        var resultRegistry = activity.registerForActivityResult(
           ThePeerResultContract(),
           activity.activityResultRegistry
       ) { chargeResult ->
           when (chargeResult) {
               is ThePeerResult.Success -> resultListener.onSuccess(chargeResult.transaction)
               is ThePeerResult.Error -> resultListener.onError(chargeResult.error)
               ThePeerResult.Cancelled -> resultListener.onCancelled()
           }
       }
        private var publicKey = getPublicKeyFromManifest(activity)

        private var meta: Map<String, String> = emptyMap()

        private fun getPublicKeyFromManifest(context: Context): String {
            val applicationInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            return applicationInfo.metaData?.getString(ThePeerConstants.PUBLIC_KEY_FROM_MANIFEST)
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

        fun build(): ThePeer {
            return ThePeer(
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
    fun sendMoney() {
        val params = ThePeerParam(
            publicKey,
            getSdkType(ThePeerSdkType.SEND_MONEY),
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
        val params = ThePeerParam(
            publicKey,
            getSdkType(ThePeerSdkType.CHECKOUT),
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
        val params = ThePeerParam(
            publicKey,
            getSdkType(ThePeerSdkType.DIRECT_CHARGE),
            amount,
            currency,
            userReference,
            null,
            meta
        )

        if (publicKey.isEmpty()) error("Add publicKey from business dashboard as meta data to manifest file. Check documentation")

        resultRegistry.launch(params)
    }


    private fun getSdkType(type: Enum<ThePeerSdkType>): String {
        return when (type) {
            ThePeerSdkType.SEND_MONEY -> {
                return ThePeerConstants.SEND_MODE
            }
            ThePeerSdkType.CHECKOUT -> {
                return ThePeerConstants.CHECKOUT_MODE
            }
            ThePeerSdkType.DIRECT_CHARGE -> {
                return ThePeerConstants.DIRECT_DEBIT
            }
            else -> ""
        }

    }
}