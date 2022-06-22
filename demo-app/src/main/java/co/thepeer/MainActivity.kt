package co.thepeer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import co.thepeer.databinding.ActivityMainBinding
import co.thepeer.sdk.ThePeer
import co.thepeer.sdk.model.ThePeerSdkType
import co.thepeer.sdk.model.ThePeerTransaction
import co.thepeer.sdk.ui.ThePeerResultListener
import co.thepeer.sdk.utils.ThePeerCurrency
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    private val INITIATE_PAYMENT_REQUEST_CODE = 1
    private val KEY_RESULT = "result_key"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize ThePeer SDK
        val thePeer = ThePeer.Builder(
            activity = this,
            amount = BigDecimal(10000000),
            currency = ThePeerCurrency.NGN,
            userReference = getString(R.string.user_reference),
            resultListener = resultListener
        ).setMeta(mapOf("city" to "Uyo")).build()

        binding.btnSendMoney.setOnClickListener {
            //calling ThePeer SDK
            thePeer.send()
        }
        binding.btnCheckout.setOnClickListener {
            thePeer.checkout("email@gmail.com")
        }
        binding.btnDirectDebit.setOnClickListener {
            thePeer.directCharge()
        }
    }

    private val resultListener = object : ThePeerResultListener {
        override fun onSuccess(transaction: ThePeerTransaction) {
            binding.resultText.text = transaction.toString()

        }

        override fun onError(error: Throwable) {
            binding.resultText.text = error.message
        }

        override fun onCancelled() {
            binding.resultText.text = " Cancelled"
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INITIATE_PAYMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.v("Demo", data?.getStringExtra(KEY_RESULT) ?: "")

        }

    }
}