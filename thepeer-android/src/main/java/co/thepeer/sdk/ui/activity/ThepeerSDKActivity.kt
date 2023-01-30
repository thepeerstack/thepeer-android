package co.thepeer.sdk.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import co.thepeer.sdk.databinding.SdkActivityBinding
import co.thepeer.sdk.model.ThepeerParam
import co.thepeer.sdk.model.ThepeerResult
import co.thepeer.sdk.ui.fragments.ThepeerFragment
import co.thepeer.sdk.utils.Logger
import co.thepeer.sdk.utils.ThepeerConstants
import co.thepeer.sdk.utils.Urls
import co.thepeer.sdk.utils.WebInterface

class ThepeerSDKActivity : AppCompatActivity() {
    private var params: ThepeerParam? = null
    private lateinit var binding: SdkActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SdkActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        params = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(
                ThepeerConstants.THE_PEER_PARAMS,
                ThepeerParam::class.java
            )
        } else {
            intent.getParcelableExtra<ThepeerParam>(ThepeerConstants.THE_PEER_PARAMS)
        }


        if (params == null) {
            throw IllegalStateException("Params should not be null. Initialize thePeer using this function  Thepeer.Builder(...)")
        }

        params?.let {
            val url = Urls.createTransactionUrl(it)
            setupWebView(url)
            Logger.log(this, url)
        }



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


        binding.webViewPeer.addJavascriptInterface(
            WebInterface { results ->
                redirectWithResult(results)
            },
            "Android"
        )

        binding.webViewPeer.loadUrl(transactionUrl)
    }

    override fun onDestroy() {
        super.onDestroy()
        params = null
        binding.webViewPeer.loadUrl("about:blank")
        binding.webViewPeer.clearHistory()
        redirectWithResult(ThepeerResult.Cancelled)
    }

    private fun redirectWithResult(result: ThepeerResult) {
        val resultData = Intent()
        resultData.putExtra(ThepeerConstants.TRANSACTION_RESULT, result)
        setResult(AppCompatActivity.RESULT_OK, resultData)
        finish()
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                params = null
            }
        }

}
