package co.thepeer.sdk.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.Window
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.webkit.WebViewCompat
import co.thepeer.sdk.R
import co.thepeer.sdk.databinding.FragmentHostDialogBinding
import co.thepeer.sdk.model.ThePeerParam
import co.thepeer.sdk.model.ThePeerResult
import co.thepeer.sdk.utils.Logger
import co.thepeer.sdk.utils.ThePeerConstants
import co.thepeer.sdk.utils.Urls
import co.thepeer.sdk.utils.WebInterface
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson


class HostDialogFragment(private val thePeerParam: ThePeerParam) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentHostDialogBinding

    companion object {
        private const val TAG = "TestFragment"

    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(transactionUrl: String) {
        binding.webViewPeer.apply {
            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                domStorageEnabled = true
            }
        }
        binding.webViewPeer.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                binding.webViewPeer.isVisible = true
            }

        }

        activity?.let {
            binding.webViewPeer.addJavascriptInterface(WebInterface { results ->
                redirectWithResult(results)
            }, "Android")
        }

        binding.webViewPeer.loadUrl(transactionUrl)
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = context?.let { BottomSheetDialog(it, R.style.CustomBottomSheetDialogTheme) }
        binding = FragmentHostDialogBinding.inflate(layoutInflater)
        val heightInPixels = ((Resources.getSystem().displayMetrics.heightPixels)).toInt()
        val params = ViewGroup.LayoutParams(MATCH_PARENT, heightInPixels)
        Log.v(TAG, heightInPixels.toString())

        dialog?.behavior?.apply {
            isDraggable = false
            isCancelable = false
            maxHeight = heightInPixels
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        dialog?.setContentView(binding.root, params)
        Log.v("Params", thePeerParam.toString())
        val url = Urls.createTransactionUrl(thePeerParam)
        Logger.log(this, url)
        setupWebView(url)


        return dialog as Dialog
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        binding.webViewPeer.loadUrl("about:blank")
        binding.webViewPeer.clearHistory()
        redirectWithResult(ThePeerResult.Cancelled)
    }

    private fun redirectWithResult(result: ThePeerResult) {
        val resultData = Intent()
        resultData.putExtra(ThePeerConstants.TRANSACTION_RESULT, result)
        activity?.setResult(AppCompatActivity.RESULT_OK, resultData)
        activity?.finish()
    }


}
