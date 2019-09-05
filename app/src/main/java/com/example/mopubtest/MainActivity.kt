package com.example.mopubtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mopub.common.MoPub
import com.mopub.common.SdkConfiguration
import com.mopub.common.SdkInitializationListener
import com.mopub.common.logging.MoPubLog
import com.mopub.common.logging.MoPubLog.LogLevel.DEBUG
import com.mopub.common.privacy.ConsentDialogListener
import com.mopub.mobileads.MoPubErrorCode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MoPub.initializeSdk(
            this,
            SdkConfiguration
                .Builder("b195f8dd8ded45fe847ad89ed1d016da")
                .withLogLevel(DEBUG)
                .build(),
            initSdkListener()
        )
    }

    private fun initSdkListener(): SdkInitializationListener? {
        return SdkInitializationListener {
            MoPub.getPersonalInformationManager()?.apply {
                if (shouldShowConsentDialog()) {
                    loadConsentDialog(object : ConsentDialogListener {
                        override fun onConsentDialogLoaded() {
                            showConsentDialog()
                        }

                        override fun onConsentDialogLoadFailed(moPubErrorCode: MoPubErrorCode) {
                            MoPubLog.i("Consent dialog failed to load.")
                        }
                    })
                }
            }
        }
    }
}
