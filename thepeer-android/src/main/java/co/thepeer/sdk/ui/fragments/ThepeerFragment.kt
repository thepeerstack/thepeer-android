package co.thepeer.sdk.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import co.thepeer.sdk.databinding.FragmentThepeerBinding
import co.thepeer.sdk.model.ThepeerParam
import co.thepeer.sdk.model.ThepeerResult
import co.thepeer.sdk.utils.Logger
import co.thepeer.sdk.utils.ThepeerConstants
import co.thepeer.sdk.utils.Urls
import co.thepeer.sdk.utils.WebInterface


class ThepeerFragment(private val thePeerParam: ThepeerParam) :
    Fragment() {

    private lateinit var binding: FragmentThepeerBinding

    companion object {
        private const val TAG = "TestFragment"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThepeerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = Urls.createTransactionUrl(thePeerParam)
        Logger.log(this, url)
        setupWebView(url)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.webViewPeer.loadUrl("about:blank")
        binding.webViewPeer.clearHistory()
        redirectWithResult(ThepeerResult.Cancelled)
    }

    private fun redirectWithResult(result: ThepeerResult) {
        val resultData = Intent()
        resultData.putExtra(ThepeerConstants.TRANSACTION_RESULT, result)
        activity?.setResult(AppCompatActivity.RESULT_OK, resultData)
        activity?.finish()
    }

}
