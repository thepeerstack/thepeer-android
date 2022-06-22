package co.thepeer.sdk.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.thepeer.sdk.databinding.SdkActivityBinding
import co.thepeer.sdk.model.ThePeerParam
import co.thepeer.sdk.ui.fragments.HostDialogFragment
import co.thepeer.sdk.utils.ThePeerConstants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ThePeerSDKActivity : AppCompatActivity() {
    private var params: ThePeerParam? = null
    private lateinit var binding: SdkActivityBinding
    lateinit var frag: BottomSheetDialogFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SdkActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        params = intent.getParcelableExtra<ThePeerParam>(ThePeerConstants.THE_PEER_PARAMS)!!

        params?.let {
            frag = HostDialogFragment(it)
            frag.show(supportFragmentManager, "home")

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

