package com.doximity.callwithdoxdialerlib;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by yinanoliver on 5/11/17.
 */

public class DoxDialerCaller {

    private static final String DOX_DIALER_PACKAGE_NAME = "com.doximity.doxdialer";

    /**
     * @param phoneNumber The phone number to dial, as a String.
     *                    It may be given in most reasonable formats, e.g.:
     *                    using numbers only: 6502333444
     *                    formatted: (650)233-3444
     *                    with a leading international area code: +1(650)233-3444
     * @param context     The Context parameter, it's used to start intent to launch the Doximity Dialer app.
     * @return true if Doximity Dialer app is successfully launched or Play Store link is successfully launched, otherwise false.
     */

    public static boolean dialPhoneNumber(Context context, String phoneNumber) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(DOX_DIALER_PACKAGE_NAME);
        if (intent == null) {
            // Doximity Dialer app is not installed, redirect to Play Store
            try {
                //Open from AppsFlyer url for marketing purposes
                String packageName = context.getPackageName();
                String appsFlyerUrl = "https://app.appsflyer.com/" + DOX_DIALER_PACKAGE_NAME + "?pid=third_party_app&c=" + packageName;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appsFlyerUrl));
                context.startActivity(intent);
                return true;
            } catch (android.content.ActivityNotFoundException e) {
                try {
                    //Play Store is installed
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + DOX_DIALER_PACKAGE_NAME));
                    context.startActivity(intent);
                    return true;
                } catch (android.content.ActivityNotFoundException exception) {
                    //Play Store is not installed on user's device, open as a web link
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + DOX_DIALER_PACKAGE_NAME));
                    context.startActivity(intent);
                    return true;
                }
            }
        } else {
            // Doximity Dialer app is installed
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }
}
