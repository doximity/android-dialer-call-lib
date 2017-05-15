# CallWithDoxDialer for Android

<p align="center">
	<a href="https://github.com/doximity/android-dialer-call-lib/"><img src="ReadMeResources/logo.png" width="200" alt="CallWithDoxDialerLib" /></a><br /><br />
	A µLibrary for making calls using <a href="https://www.doximity.com/clinicians/download/dialer/">Doximity Dialer</a>.<br /><br />
</p>
<br />


[![GitHub release](https://img.shields.io/github/release/doximity/android-dialer-call-lib.svg)](https://github.com/doximity/android-dialer-call-lib/releases) ![platforms](https://img.shields.io/badge/platforms-android-green.svg)

## What is CallWithDoxDialer?

Doximity's [Dialer][] app lets healthcare professionals make phone calls to patients while on the go -- without revealing personal phone numbers. Calls are routed through Doximity's HIPPA-secure platform and relayed to the patient who will see the doctor's office number in the Caller ID. Doximity Dialer is currently available to verified physicians, nurse practitioners, physician assistants and pharmacists in the United States. 

CallWithDoxDialer is a mobile library for Android that lets 3rd-party apps easily initiate calls through the Doximity Dialer app.


## Other Platforms

* [iOS](https://github.com/doximity/CallWithDoxDialer)

## Sample App

A sample project which provides runnable code examples that demonstrate uses of the classes in this project is available in the `/CallWithDoxDialerSample` folder. 

![Dox Dialer installed](ReadMeResources/dox-dialer-installed.gif)
![Dox Dialer not installed](ReadMeResources/dox-dialer-not-installed.gif)


## Download

Method 1:
Add JitPack repository in your root project build.gradle:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Grab the latest version via Gradle, just add the dependency to your app's `build.gradle` file:
```groovy
dependencies {  
    compile 'com.github.doximity:android-dialer-call-lib:v1.0'
}
```

Method 2: Download the library and import it as a module.
in `Settings.gradle`
```groovy
include ':YourApp', ':CallWithDoxDialerLib'
```

then add the dependency to your app's `build.gradle` file:
```groovy
dependencies {  
    compile project(':CallWithDoxDialerLib')
}
```

## Using CallWithDoxDialer

### Core Functionality
To initiate a call using Doximity Dialer, simply call the `DoxDialerCaller.dialPhoneNumber(Context context, String phoneNumber)` method.
If the Doximity Dialer app is not installed, this call will direct the user to Doximity Dialer on the Play Store.

Most reasonable phone number formats are accepted by the `dialPhoneNumber` method, e.g.:
- using numbers only: `4151234567`
- formatted: `(415)123-4567`
- with a leading international area code: `+1(415)123-4567`

```java
   DoxDialerCaller.dialPhoneNumber(DialActivity.this, "4151234567");
   DoxDialerCaller.dialPhoneNumber(DialActivity.this, "+1(415)123-4567");
```


### Icons
The library also includes a version of the Doximity Dialer icon appropriate for use inside Button or ImageView.
It's available in the `/DoxDialerIconDrawables` folder.


## Have a question?
If you need any help, please reach out! <dialer@doximity.com>.


[Dialer]: https://www.doximity.com/clinicians/download/dialer
