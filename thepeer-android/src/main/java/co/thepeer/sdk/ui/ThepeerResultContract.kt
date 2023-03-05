package co.thepeer.sdk.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import co.thepeer.sdk.model.ThepeerParam
import co.thepeer.sdk.model.ThepeerResult
import co.thepeer.sdk.ui.activity.ThepeerSdkActivity
import co.thepeer.sdk.utils.ThepeerConstants

internal class ThepeerResultContract : ActivityResultContract<ThepeerParam, ThepeerResult>() {
    override fun createIntent(context: Context, input: ThepeerParam): Intent {
        return Intent(context, ThepeerSdkActivity::class.java).apply {
            putExtra(ThepeerConstants.THE_PEER_PARAMS, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ThepeerResult {
        if (resultCode == Activity.RESULT_CANCELED) {
            return ThepeerResult.Cancelled
        }

        if (resultCode == Activity.RESULT_OK && intent != null) {
            return intent.getParcelableExtra(ThepeerConstants.TRANSACTION_RESULT)
                ?: error(NO_RESULT_ERROR)
        }

        error(NO_RESULT_ERROR)
    }

    companion object {
        const val NO_RESULT_ERROR =
            "Transaction was not confirmed for some reason. Please contact ThePeer support"
    }
}
