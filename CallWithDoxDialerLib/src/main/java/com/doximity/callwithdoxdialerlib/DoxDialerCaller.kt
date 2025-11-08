package com.doximity.callwithdoxdialerlib

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

/**
 *
 */
object DoxDialerCaller {
    private const val DOXIMITY_PACKAGE_NAME = "com.doximity.doximitydroid"

    /**
     * Launches the Doximity app with the specified phone number prefilled in the Dialer dialpad.
     * The user may then select voice call, video call, or text options.
     *
     * @param context     The Context parameter, used to start intent to launch the Doximity app.
     * @param phoneNumber The phone number to dial, as a String.
     * It may be given in most reasonable formats, e.g.:
     * using numbers only: 6502333444
     * formatted: (650)233-3444
     * with a leading international area code: +1(650)233-3444
     * @return true if Doximity Dialer app is successfully launched or Play Store link is successfully launched, otherwise false.
     */
    fun dialPhoneNumber(context: Context, phoneNumber: String) = makeCall(
            context,
            phoneNumber,
            CallType.VOICE_PREFILL,
        )

    /**
     * Launches the Doximity app and immediately starts a voice call with the specified phone number.
     *
     * @param context     The Context parameter, used to start intent to launch the Doximity app.
     * @param phoneNumber The phone number to call via voice, as a String.
     * It may be given in most reasonable formats, e.g.:
     * using numbers only: 6502333444
     * formatted: (650)233-3444
     * with a leading international area code: +1(650)233-3444
     * @return true if Doximity Dialer app is successfully launched or Play Store link is successfully launched, otherwise false.
     */
    fun startVoiceCall(context: Context, phoneNumber: String) = makeCall(
        context,
        phoneNumber,
        CallType.VOICE_DIAL,
    )

    /**
     * Launches the Doximity app and immediately starts a video call with the specified phone number.
     *
     * @param context     The Context parameter, used to start intent to launch the Doximity app.
     * @param phoneNumber The phone number to call via video, as a String.
     * It may be given in most reasonable formats, e.g.:
     * using numbers only: 6502333444
     * formatted: (650)233-3444
     * with a leading international area code: +1(650)233-3444
     * @return true if Doximity Dialer app is successfully launched or Play Store link is successfully launched, otherwise false.
     */
    fun startVideoCall(context: Context, phoneNumber: String) = makeCall(
        context,
        phoneNumber,
        CallType.VIDEO_DIAL,
    )

    private fun makeCall(
        context: Context,
        phoneNumber: String,
        type: CallType,
    ): Boolean {
        return context.packageManager.getLaunchIntentForPackage(DOXIMITY_PACKAGE_NAME)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            when (type) {
                CallType.VOICE_PREFILL -> {
                    setData("doximity://dialer/call&target_number=$phoneNumber".toUri())
                }

                CallType.VOICE_DIAL -> {
                    setData("doximity://dialer/call/voice?target_number=$phoneNumber".toUri())
                }

                CallType.VIDEO_DIAL -> {
                    setData("doximity://dialer/call/video?target_number=$phoneNumber".toUri())
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
        VOICE_PREFILL,
        VOICE_DIAL,
        VIDEO_DIAL,
    }
}
