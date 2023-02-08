package co.thepeer.sdk.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import co.thepeer.sdk.R
import co.thepeer.sdk.databinding.SdkActivityBinding
import co.thepeer.sdk.model.ThepeerParam
import co.thepeer.sdk.model.ThepeerResult
import co.thepeer.sdk.utils.*
import co.thepeer.sdk.utils.Logger
import co.thepeer.sdk.utils.Urls
import co.thepeer.sdk.utils.WebInterface

internal class ThepeerSdkActivity : AppCompatActivity() {
    private var params: ThepeerParam? = null
    private var url: String = ""
    private lateinit var binding: SdkActivityBinding
    private var isLoaded = false
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
            url = Urls.createTransactionUrl(it)
            showWebView()
            Logger.log(this, url)
        }

        binding.includeRetryView.closeBtn.setOnClickListener {
            finish()
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
                isLoaded = true
                binding.webViewPeer.isVisible = true
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                if (error != null) {
                    showNetworkErrorRetryView()
                }
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                startActivity(Intent(Intent.ACTION_VIEW, request?.url))
                return true
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
        clearWebViewValues()
        redirectWithResult(ThepeerResult.Cancelled)
    }

    private fun clearWebViewValues() {
        params = null
        binding.webViewPeer.loadUrl("about:blank")
        binding.webViewPeer.clearHistory()
    }

    private fun showNetworkErrorRetryView() {
        if (!isNetworkConnectionAvailable) {
            if (!isLoaded) {
                hideWebView()
                binding.includeRetryView.retryView.isVisible = true
            } else {
                Toast.makeText(this, getString(R.string.retry_text), Toast.LENGTH_LONG)
                    .show()
            }

        } else {
            Toast.makeText(this, "Something went wrong. Contact thePeer support", Toast.LENGTH_LONG)
                .show()
        }

        binding.includeRetryView.retryButton.setOnClickListener {
            if (isNetworkConnectionAvailable && url.isNotBlank()) {
                hideRetryView()
                setupWebView(url)
            }
        }
    }

    private fun hideRetryView(){
        binding.includeRetryView.retryView.isVisible = false
        binding.loading.isVisible = true
    }

    private fun showWebView() {
        binding.loading.isVisible = true
        if (isNetworkConnectionAvailable) {
            setupWebView(url)
        } else {
            showNetworkErrorRetryView()
        }
    }

    private fun hideWebView() {
        binding.webViewPeer.isVisible = false
        binding.loading.isVisible = false
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
                if(!isNetworkConnectionAvailable){
                    finish()
                }
            }
        }

}
