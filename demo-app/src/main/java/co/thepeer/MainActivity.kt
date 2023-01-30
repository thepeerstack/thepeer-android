package co.thepeer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import co.thepeer.databinding.ActivityMainBinding
import co.thepeer.sdk.Thepeer
import co.thepeer.sdk.model.ThepeerTransaction
import co.thepeer.sdk.ui.ThepeerResultListener
import co.thepeer.sdk.utils.ThepeerCurrency
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    private val INITIATE_PAYMENT_REQUEST_CODE = 1
    private val KEY_RESULT = "result_key"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize Thepeer SDK
        val thepeer = Thepeer.Builder(
            activity = this,
            amount = BigDecimal(10000),
            currency = ThepeerCurrency.NGN,
            userReference = getString(R.string.user_reference),
            resultListener = resultListener
        ).setMeta(mapOf("city" to "Uyo")).build()

        binding.btnSendMoney.setOnClickListener {
            //calling Thepeer SDK
            thepeer.send()
        }
        binding.btnCheckout.setOnClickListener {
            thepeer.checkout("email@gmail.com")
        }
        binding.btnDirectDebit.setOnClickListener {
            thepeer.directCharge()
        }
    }

    private val resultListener = object : ThepeerResultListener {
        override fun onSuccess(transaction: ThepeerTransaction) {
            binding.resultText.text = transaction.toString()

        }

        override fun onError(error: Throwable) {
            binding.resultText.text = error.message
        }

        override fun onCancelled() {
            binding.resultText.text = " Cancelled"
        }

    }

}