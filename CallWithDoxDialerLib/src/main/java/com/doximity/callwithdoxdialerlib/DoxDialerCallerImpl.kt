package com.doximity.callwithdoxdialerlib

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

/**
 * Default implementation of [DoxDialerCaller].
 *
 * This class is internal and should not be used directly.
 * Use [DoxDialerCaller.getInstance] to get an instance.
 */
internal class DoxDialerCallerImpl : DoxDialerCaller {

    override fun dialPhoneNumber(context: Context, phoneNumber: String): Boolean {
        return makeCall(context, phoneNumber, CallType.PREFILL_DIALPAD)
    }

    override fun startVoiceCall(context: Context, phoneNumber: String): Boolean {
        return makeCall(context, phoneNumber, CallType.VOICE_DIAL)
    }

    override fun startVideoCall(context: Context, phoneNumber: String): Boolean {
        return makeCall(context, phoneNumber, CallType.VIDEO_DIAL)
    }

    private fun makeCall(
        context: Context,
        phoneNumber: String,
        type: CallType,
    ): Boolean {
        return context.packageManager.getLaunchIntentForPackage(DOXIMITY_PACKAGE_NAME)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            when (type) {
                CallType.PREFILL_DIALPAD -> {
                    setData("https://www.doximity.com/dialer/call?target_number=$phoneNumber".toUri())
                }

                CallType.VOICE_DIAL -> {
                    setData("https://www.doximity.com/dialer/call/voice?target_number=$phoneNumber".toUri())
                }

                CallType.VIDEO_DIAL -> {
                    setData("https://www.doximity.com/dialer/call/video?target_number=$phoneNumber".toUri())
                }
            }
        }?.let { intent ->
            try {
                context.startActivity(intent)
                true
            } catch (_: Exception) {
                false
            }
        } ?: run {
            launchPlayStoreToGetDoxApp(context)
            true
        }
    }

    private fun launchPlayStoreToGetDoxApp(context: Context) {
        val playStoreIntent = Intent(
            Intent.ACTION_VIEW,
            "market://details?id=$DOXIMITY_PACKAGE_NAME".toUri()
        )
        val playStoreInstalled = playStoreIntent.resolveActivity(context.packageManager) != null
        if (playStoreInstalled) {
            //Open from Singular url for marketing purposes
            val packageName = context.packageName
            val singularUrl = "https://doximity.sng.link/Az2j3/c17w?_dl=https://www.doximity.com/dialer/home&_smtype=3&pid=third_party_app&c=$packageName"
            context.startActivity(
                Intent(Intent.ACTION_VIEW, singularUrl.toUri())
            )
        } else {
            //Play Store is not installed on user's device, open as a web link
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "https://play.google.com/store/apps/details?id=$DOXIMITY_PACKAGE_NAME".toUri()
                )
            )
        }
    }

    private enum class CallType {
        PREFILL_DIALPAD,
        VOICE_DIAL,
        VIDEO_DIAL,
    }

    companion object {
        private const val DOXIMITY_PACKAGE_NAME = "com.doximity.doximitydroid"
    }
}
