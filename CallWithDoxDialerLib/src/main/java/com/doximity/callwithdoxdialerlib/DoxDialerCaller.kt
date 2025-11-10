package com.doximity.callwithdoxdialerlib

import android.content.Context

/**
 * Interface for making calls through Doximity Dialer.
 *
 * Use [DoxDialerCaller.getInstance] to get the default implementation.
 */
interface DoxDialerCaller {

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
    fun dialPhoneNumber(context: Context, phoneNumber: String): Boolean

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
    fun startVoiceCall(context: Context, phoneNumber: String): Boolean

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
    fun startVideoCall(context: Context, phoneNumber: String): Boolean

    companion object {
        private val doxDialerCaller: DoxDialerCaller by lazy { DoxDialerCallerImpl() }

        /**
         * Gets the default implementation of [DoxDialerCaller].
         *
         * @return The singleton instance of [DoxDialerCaller]
         */
        @JvmStatic
        fun getInstance(): DoxDialerCaller = doxDialerCaller
    }
}
