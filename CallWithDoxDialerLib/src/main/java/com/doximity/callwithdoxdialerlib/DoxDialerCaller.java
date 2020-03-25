package com.doximity.callwithdoxdialerlib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by yinanoliver on 5/11/17.
 */

public class DoxDialerCaller {

    private static final String DOXIMITY_PACKAGE_NAME = "com.doximity.doximitydroid";

    /**
     * @param phoneNumber The phone number to dial, as a String.
     *                    It may be given in most reasonable formats, e.g.:
     *                    using numbers only: 6502333444
     *                    formatted: (650)233-3444
     *                    with a leading international area code: +1(650)233-3444
     * @param context     The Context parameter, it's used to start intent to launch the Doximity Dialer app.
     * @return true if Doximity Dialer app is successfully launched or Play Store link is successfully launched, otherwise false.
     */

    public static boolean dialPhoneNumber(@NonNull Context context, @NonNull String phoneNumber) {
        PackageManager packageManager = context.getPackageManager();
        Intent launchDialerIntent = packageManager.getLaunchIntentForPackage(DOXIMITY_PACKAGE_NAME);
        boolean doxDialerAppInstalled = launchDialerIntent != null;

        if (doxDialerAppInstalled) {
            launchDialerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchDialerIntent.setAction(Intent.ACTION_DIAL);
            launchDialerIntent.setData(Uri.parse("tel:" + phoneNumber));
            if (launchDialerIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(launchDialerIntent);
                return true;
            }
        } else {
            Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + DOXIMITY_PACKAGE_NAME));
            boolean playStoreInstalled = playStoreIntent.resolveActivity(packageManager) != null;
            if (playStoreInstalled) {
                //Open from AppsFlyer url for marketing purposes
                String packageName = context.getPackageName();
                String appsFlyerUrl = "https://app.appsflyer.com/" + DOXIMITY_PACKAGE_NAME + "?pid=third_party_app&c=" + packageName;
                launchDialerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appsFlyerUrl));
                context.startActivity(launchDialerIntent);
                return true;
            } else {
                //Play Store is not installed on user's device, open as a web link
                launchDialerIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + DOXIMITY_PACKAGE_NAME));
                context.startActivity(launchDialerIntent);
                return true;
            }
        }
        return false;
    }
}
