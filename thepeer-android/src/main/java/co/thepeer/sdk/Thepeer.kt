package co.thepeer.sdk

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import co.thepeer.sdk.model.ThepeerConfig
import co.thepeer.sdk.model.ThepeerParam
import co.thepeer.sdk.model.ThepeerResult
import co.thepeer.sdk.model.ThepeerSDKType
import co.thepeer.sdk.ui.ThepeerResultContract
import co.thepeer.sdk.ui.ThepeerResultListener
import co.thepeer.sdk.utils.ThepeerConstants

/**
 * This is Thepeer SDK class instance.
 * @param publicKey -> Required to authenticate the merchant
 * @param userReference -> Customer indexed user reference from ThePeer
 * @param resultRegistry -> used to register the activity for result
 * @param activity -> Required to launch the drop in UI activity
 */
class Thepeer internal constructor(
    private val publicKey: String,
    private val userReference: String,
    private var resultRegistry: ActivityResultLauncher<ThepeerParam>,
    private val activity: AppCompatActivity,
) {

    /**
     * Config builder for initialisation parameters
     * @param userReference -> Customer indexed user reference from ThePeer
     * @param activity -> Required to launch the drop in UI activity
     */
    class Initiate(
        private val userReference: String,
        private val activity: AppCompatActivity,
        private val resultListener: ThepeerResultListener,

    ) {
        private var publicKey = ""

        val resultRegistry = activity.registerForActivityResult(
            ThepeerResultContract(),
            activity.activityResultRegistry,
        ) { chargeResult ->
            when (chargeResult) {
                is ThepeerResult.Success -> resultListener.onSuccess(chargeResult.response)
                is ThepeerResult.Error -> resultListener.onError(Throwable(chargeResult.error))
                ThepeerResult.Cancelled -> resultListener.onCancelled()
            }
        }

        init {
            publicKey = getPublicKeyFromManifest(activity)

            if (publicKey.isEmpty()) {
                error(
                    "Add publicKey from business " +
                        "dashboard as meta data to manifest file. Check documentation",
                )
            }
        }

        private fun getPublicKeyFromManifest(context: Context): String {
            val applicationInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA,
            )
            return applicationInfo.metaData?.getString(ThepeerConstants.PUBLIC_KEY_FROM_MANIFEST)
                .orEmpty()
        }

        fun build(): Thepeer {
            return Thepeer(
                publicKey,
                userReference,
                resultRegistry,
                activity,
            )
        }
    }

    /**
     * This function will be called to launch ThePeer Send Money Widget.
     * @param config is the transaction specific configurations.
     */
    fun send(
        config: ThepeerConfig,
    ) {
        val params = ThepeerParam(
            publicKey,
            getSdkType(ThepeerSDKType.SEND),
            config.amount,
            config.currency,
            userReference,
            null,
            config.meta,
        )
        resultRegistry.launch(params)
    }

    /**
     * This function will be called to launch ThePeer Checkout Widget
     * @param emailAddress is the email address of the user.
     * @param config is the transaction specific configurations.
     */
    fun checkout(
        emailAddress: String,
        config: ThepeerConfig,
    ) {
        val params = ThepeerParam(
            publicKey,
            getSdkType(ThepeerSDKType.CHECKOUT),
            config.amount,
            config.currency,
            userReference,
            emailAddress,
            config.meta,
        )

        resultRegistry.launch(params)
    }

    /**
     * This function will be called to launch ThePeer Direct Charge Widget
     * @param config is the transaction specific configurations.
     */
    fun directCharge(
        config: ThepeerConfig,
    ) {
        val params = ThepeerParam(
            publicKey,
            getSdkType(ThepeerSDKType.DIRECT_CHARGE),
            config.amount,
            config.currency,
            userReference,
            null,
            config.meta,
        )
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
