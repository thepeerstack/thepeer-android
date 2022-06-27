package co.thepeer.sdk.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.thepeer.sdk.databinding.SdkActivityBinding
import co.thepeer.sdk.model.ThepeerParam
import co.thepeer.sdk.ui.fragments.HostDialogFragment
import co.thepeer.sdk.utils.ThepeerConstants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ThepeerSdkActivity : AppCompatActivity() {
    private var params: ThepeerParam? = null
    private lateinit var binding: SdkActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SdkActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        params = intent.getParcelableExtra<ThepeerParam>(ThepeerConstants.THE_PEER_PARAMS)!!

        params?.let {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, HostDialogFragment(it))
                .commit()
        }

    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        params = null
    }

}

