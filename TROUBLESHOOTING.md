*Step-by-step diagnostic workflows, error log resolution, and troubleshooting runbooks for the DEEN Commerce Android wrapper.*

# TROUBLESHOOTING.md — Diagnostic Runbooks & Error Solutions

---

## 📑 Table of Contents

- [1. Diagnostic Tools & Inspection Methods](#1-diagnostic-tools--inspection-methods)
- [2. White Screen / Blank Screen on Launch](#2-white-screen--blank-screen-on-launch)
- [3. Gradle Sync or Android Build Failures](#3-gradle-sync--android-build-failures)
- [4. App Crashes Immediately Upon Launch](#4-app-crashes-immediately-upon-launch)
- [5. Hardware Back Button Exits App Unintentionally](#5-hardware-back-button-exits-app-unintentionally)
- [6. Session Cookies & Login Dropped on App Relaunch](#6-session-cookies--login-dropped-on-app-relaunch)
- [7. Android App Links / Deep Links Fail to Resolve](#7-android-app-links--deep-links-fail-to-resolve)
- [8. Slow Initial Page Load & Perceived Latency](#8-slow-initial-page-load--perceived-latency)
- [9. Push Notifications Not Received on Device](#9-push-notifications-not-received-on-device)
- [10. Cleartext HTTP Traffic Blocked by Android](#10-cleartext-http-traffic-blocked-by-android)

---

## 1. Diagnostic Tools & Inspection Methods

When encountering runtime or build issues, leverage these primary diagnostic utilities:

### 1.1 Remote Chrome DevTools (WebView Inspection)
Inspect JavaScript exceptions, DOM state, network waterfalls, and console logs running inside the Android WebView:
1. Connect your Android device via USB with USB Debugging enabled.
2. Open Google Chrome on your computer and navigate to:
   ```text
   chrome://inspect/#devices
   ```
3. Locate **DEEN Commerce** (`com.deencommerce.app`) under Remote Target and click **Inspect**.

### 1.2 Android Logcat (Native Crash Logs)
Filter device system logs for Capacitor and Android runtime errors:
```bash
# Filter specifically for Capacitor and WebView events
adb logcat -s Capacitor:V chromium:V AndroidRuntime:E

# Or stream all fatal exceptions
adb logcat *:E | grep -i deencommerce
```

### 1.3 Gradle Diagnostic Flags
To debug failing native Gradle compilations:
```bash
cd android
./gradlew assembleDebug --stacktrace --info
```

---

## 2. White Screen / Blank Screen on Launch

### Symptoms
App launches, shows splash screen briefly, and then renders an empty white screen.

### Root Causes & Checklist
1. **Network Connectivity Failure:** Ensure the device or emulator has active internet access.
2. **Missing Internet Permission:** Verify `android/app/src/main/AndroidManifest.xml` contains:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
   ```
3. **Invalid `server.url` Configuration:** Open `capacitor.config.ts` and verify:
   ```typescript
   server: {
     url: 'https://deencommerce.com/',
     cleartext: false
   }
   ```
   *Remember to run `npx cap sync android` after modifying this file.*
4. **SSL / Certificate Errors:** If testing with a self-signed staging domain, the WebView blocks connection silently. Connect via `chrome://inspect` to view SSL errors.

---

## 3. Gradle Sync or Android Build Failures

### Symptoms
Build terminates with errors such as `Unsupported class file major version`, `Task :app:compileDebugJavaWithJavac FAILED`, or AAPT resource errors.

### Solutions
- **Verify Java Version (JDK 17 Required):**
  ```bash
  java -version
  # Ensure version is 17.x. Android Gradle Plugin 8+ requires JDK 17.
  ```
- **Clean Gradle Cache & Daemon:**
  ```bash
  cd android
  ./gradlew clean --no-daemon
  rm -rf .gradle/
  ```
- **Invalidate Android Studio Caches:**
  In Android Studio: Click **File > Invalidate Caches / Restart > Invalidate and Restart**.

---

## 4. App Crashes Immediately Upon Launch

### Symptoms
App closes instantly after tapping the icon.

### Diagnostic Steps
1. Run `adb logcat -d *:E > crash_log.txt` and inspect the bottom of the file.
2. **Corrupted `capacitor.config.json`:** If JSON syntax errors exist, the native Capacitor runtime fails to boot. Regenerate via:
   ```bash
   npx cap sync android
   ```
3. **Unregistered Custom Plugins:** If you added a custom native plugin, ensure it is properly registered or annotated with `@CapacitorPlugin`.

---

## 5. Hardware Back Button Exits App Unintentionally

### Symptoms
Pressing the physical or gesture back button terminates the app instead of navigating back in the product catalog.

### Solution
Ensure the `@capacitor/app` listener is active in your frontend codebase:
```typescript
import { App } from '@capacitor/app';

App.addListener('backButton', ({ canGoBack }) => {
  if (canGoBack) {
    window.history.back();
  } else {
    // Show exit confirmation or exit
    App.exitApp();
  }
});
```

---

## 6. Session Cookies & Login Dropped on App Relaunch

### Symptoms
Users are logged out every time the application is closed or restarted.

### Solutions
1. **Enable Third-Party & Hardware Cookies in `MainActivity.java`:**
   ```java
   import android.webkit.CookieManager;

   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       CookieManager cookieManager = CookieManager.getInstance();
       cookieManager.setAcceptCookie(true);
       cookieManager.setAcceptThirdPartyCookies(this.bridge.getWebView(), true);
   }
   ```
2. **Flush Cookies on Lifecycle Events:**
   ```java
   @Override
   public void onPause() {
       super.onPause();
       CookieManager.getInstance().flush();
   }
   ```
3. **Validate Cookie Attributes:** Ensure authentication cookies returned by `deencommerce.com` do not specify an invalid `Domain` or expired `Max-Age`.

---

## 7. Android App Links / Deep Links Fail to Resolve

### Symptoms
Clicking a store link in SMS or messaging apps opens the standard mobile browser instead of the DEEN Android app.

### Solutions
1. **Declare Intent Filters:** Ensure `android/app/src/main/AndroidManifest.xml` contains the verified intent filter:
   ```xml
   <intent-filter android:autoVerify="true">
       <action android:name="android.intent.action.VIEW" />
       <category android:name="android.intent.category.DEFAULT" />
       <category android:name="android.intent.category.BROWSABLE" />
       <data android:scheme="https" android:host="deencommerce.com" />
   </intent-filter>
   ```
2. **Verify Digital Asset Links:** Verify your `assetlinks.json` is publicly hosted at:
   `https://deencommerce.com/.well-known/assetlinks.json`
   Test with Google's verification tool:
   ```bash
   curl -I https://deencommerce.com/.well-known/assetlinks.json
   ```

---

## 8. Slow Initial Page Load & Perceived Latency

### Symptoms
White gap or delay between native splash screen and visible web storefront.

### Solutions
- **Extend Splash Duration Until First Paint:**
  In `capacitor.config.ts`, set `SplashScreen.launchAutoHide: false`.
  In your web application entry point (`DOMContentLoaded` or React root mount), trigger:
  ```typescript
  import { SplashScreen } from '@capacitor/splash-screen';
  SplashScreen.hide({ fadeOutDuration: 200 });
  ```
- **Leverage HTTP Caching & CDN:** Verify that static scripts, web fonts, and images on `deencommerce.com` return proper `Cache-Control: public, max-age=31536000` headers.

---

## 9. Push Notifications Not Received on Device

### Symptoms
Test push payloads sent from Firebase Console do not appear in the notification drawer.

### Solutions
1. Verify `android/app/google-services.json` is present and matches the package name `com.deencommerce.app`.
2. Check that the notification channel is registered on Android 8.0+ devices:
   ```typescript
   import { PushNotifications } from '@capacitor/push-notifications';
   await PushNotifications.createChannel({
     id: 'deen_promotions',
     name: 'DEEN Commerce Offers',
     importance: 4,
   });
   ```
3. On Android 13+ (API 33+), ensure runtime notification permission was requested:
   ```typescript
   await PushNotifications.requestPermissions();
   ```

---

## 10. Cleartext HTTP Traffic Blocked by Android

### Symptoms
Console displays: `net::ERR_CLEARTEXT_NOT_PERMITTED` when connecting to `http://localhost` or `http://10.0.2.2`.

### Solution
In development only, enable cleartext traffic in `android/app/src/main/AndroidManifest.xml`:
```xml
<application
    android:usesCleartextTraffic="true"
    ...>
```
*Note: Cleartext traffic MUST be disabled before publishing release builds (see [RULES.md](./RULES.md)).*

---

*See also: [FAQ.md](./FAQ.md) for architectural explanations and [TESTING.md](./TESTING.md) for quality verification.*
