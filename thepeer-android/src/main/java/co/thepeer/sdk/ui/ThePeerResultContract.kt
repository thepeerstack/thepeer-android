package co.thepeer.sdk.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import co.thepeer.sdk.model.ThePeerParam
import co.thepeer.sdk.model.ThePeerResult
import co.thepeer.sdk.ui.activity.ThePeerSDKActivity
import co.thepeer.sdk.utils.ThePeerConstants

internal class ThePeerResultContract: ActivityResultContract<ThePeerParam, ThePeerResult>() {
    override fun createIntent(context: Context, input: ThePeerParam): Intent {
       return Intent(context, ThePeerSDKActivity::class.java).apply {
           putExtra(ThePeerConstants.THE_PEER_PARAMS, input)
       }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ThePeerResult {
        if (resultCode == Activity.RESULT_CANCELED) {
            return ThePeerResult.Cancelled
        }

        if (resultCode == Activity.RESULT_OK && intent != null) {
            return intent.getParcelableExtra(ThePeerConstants.TRANSACTION_RESULT)
                ?: error(NO_RESULT_ERROR)
        }

        error(NO_RESULT_ERROR)
    }

    companion object {
        const val NO_RESULT_ERROR =
            "Transaction was not confirmed for some reason. Please contact ThePeer support"
    }
}