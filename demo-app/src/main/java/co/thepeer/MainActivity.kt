package co.thepeer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.thepeer.databinding.ActivityMainBinding
import co.thepeer.sdk.Thepeer
import co.thepeer.sdk.model.ThepeerConfig
import co.thepeer.sdk.ui.ThepeerResultListener
import co.thepeer.sdk.utils.ThepeerCurrency
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var thepeer: Thepeer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize Thepeer SDK
        val thepeer = Thepeer.Initiate(
            activity = this,
            userReference = getString(R.string.user_reference),
            resultListener = resultListener,
        ).build()

        binding.btnSendMoney.setOnClickListener {
            // calling ThePeer SDK
            val config = ThepeerConfig(amount = BigDecimal(100000), currency = ThepeerCurrency.NGN)
            thepeer.send(config = config)
        }
        binding.btnCheckout.setOnClickListener {
            val config = ThepeerConfig(amount = BigDecimal(100000), currency = ThepeerCurrency.NGN)
            thepeer.checkout("email@gmail.com", config = config)
        }
        binding.btnDirectDebit.setOnClickListener {
            val config = ThepeerConfig(amount = BigDecimal(100000), currency = ThepeerCurrency.NGN)
            thepeer.directCharge(config)
        }
    }

    private val resultListener = object : ThepeerResultListener {
        override fun onSuccess(response: String) {
            binding.resultText.text = response
        }

        override fun onError(error: Throwable) {
            binding.resultText.text = error.message
        }

        override fun onCancelled() {
            binding.resultText.text = " Cancelled"
        }
    }
}
