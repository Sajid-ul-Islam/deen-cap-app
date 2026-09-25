*Answers to frequently asked questions regarding development, WebView architecture, native plugins, and deployment.*

# FAQ.md — Frequently Asked Questions

---

## 📑 Table of Contents

- [1. General Architecture & Web Wrapping](#1-general-architecture--web-wrapping)
  - [Q1: Why does the app show a blank or white screen on launch?](#q1-why-does-the-app-show-a-blank-or-white-screen-on-launch)
  - [Q2: How do we update website content without submitting a new app to Google Play?](#q2-how-do-we-update-website-content-without-submitting-a-new-app-to-google-play)
  - [Q3: How do I change the remote storefront URL?](#q3-how-do-i-change-the-remote-storefront-url)
- [2. Native Features & Plugins](#2-native-features--plugins)
  - [Q4: How do I install and configure a new native Capacitor plugin?](#q4-how-do-i-install-and-configure-a-new-native-capacitor-plugin)
  - [Q5: How do I enable push notifications using Firebase Cloud Messaging?](#q5-how-do-i-enable-push-notifications-using-firebase-cloud-messaging)
  - [Q6: How does authentication and cookie persistence work in WebView?](#q6-how-does-authentication-and-cookie-persistence-work-in-webview)
- [3. Builds & Optimization](#3-builds--optimization)
  - [Q7: What is the difference between an APK and an AAB?](#q7-what-is-the-difference-between-an-apk-and-an-aab)
  - [Q8: How can we reduce the final APK/AAB download size?](#q8-how-can-we-reduce-the-final-apkaab-download-size)
- [4. Device Testing & Debugging](#4-device-testing--debugging)
  - [Q9: How do I test the app on a physical Android device?](#q9-how-do-i-test-the-app-on-a-physical-android-device)
  - [Q10: Can the app work completely offline?](#q10-can-the-app-work-completely-offline)

---

## 1. General Architecture & Web Wrapping

### Q1: Why does the app show a blank or white screen on launch?
A white or blank screen usually indicates one of four issues:
1. **Network Connectivity Failure:** The device cannot reach `https://deencommerce.com/` (e.g., device offline or airplane mode active).
2. **Missing Internet Permission:** Check that `android/app/src/main/AndroidManifest.xml` includes `<uses-permission android:name="android.permission.INTERNET" />`.
3. **Cleartext Traffic Blocked:** If pointing to an insecure `http://` address, Android 9+ blocks it by default. Set `server.cleartext: true` in development or enforce HTTPS.
4. **JavaScript Runtime Exception:** An uncaught error occurred on the remote site before the initial render. Connect Chrome DevTools via `chrome://inspect/#devices` to inspect the WebView console logs.

---

### Q2: How do we update website content without submitting a new app to Google Play?
Because the application loads `https://deencommerce.com/` remotely inside the WebView, **any update deployed to the production web server appears instantly inside the mobile app!**
You only need to publish a new native binary to Google Play when you:
- Add or update native Capacitor plugins.
- Modify app permissions in `AndroidManifest.xml`.
- Change native splash screens or launcher icons.
- Update native Android SDK target levels.

---

### Q3: How do I change the remote storefront URL?
Modify the `server.url` property inside `capacitor.config.ts`:

```typescript
const config: CapacitorConfig = {
  appId: 'com.deencommerce.app',
  appName: 'DEEN Commerce',
  server: {
    url: 'https://staging.deencommerce.com/', // Update target URL
  },
};
```
Then execute:
```bash
npx cap sync android
```

---

## 2. Native Features & Plugins

### Q4: How do I install and configure a new native Capacitor plugin?
Follow this three-step workflow:
```bash
# 1. Install via npm
npm install @capacitor/camera

# 2. Synchronize native Android project bindings
npx cap sync android

# 3. Add any required permissions to android/app/src/main/AndroidManifest.xml
```
Capacitor automatically registers the native Java plugin code through Android reflection during `npx cap sync`.

---

### Q5: How do I enable push notifications using Firebase Cloud Messaging?
1. Create a Firebase project in the [Firebase Console](https://console.firebase.google.com/).
2. Download your project's `google-services.json` file.
3. Place `google-services.json` inside `android/app/` (do NOT commit this file to public git).
4. Install `@capacitor/push-notifications`:
   ```bash
   npm install @capacitor/push-notifications
   npx cap sync android
   ```
5. Apply the Google Services Gradle plugin in `android/app/build.gradle`.
6. Register push token listeners in your frontend bridge scripts.

---

### Q6: How does authentication and cookie persistence work in WebView?
Capacitor's Android WebView delegates cookie storage to Android's native `CookieManager`. Cookies set via `Set-Cookie` response headers or `document.cookie` are written to a persistent SQLite database on the Android filesystem. Even when the user swipes the app away from recent tasks or reboots their phone, the session cookies persist.

---

## 3. Builds & Optimization

### Q7: What is the difference between an APK and an AAB?
- **APK (Android Package Kit):** A standalone executable binary containing all assets, compiled code, and native libraries for all CPU architectures. Used for direct testing and ad-hoc distribution.
- **AAB (Android App Bundle):** Google's publishing format. You upload the AAB to the Google Play Console, and Google automatically generates optimized device-specific APKs tailored to each user's screen resolution and CPU architecture (arm64-v8a, armeabi-v7a, x86). This reduces user download sizes by 30–50%.

---

### Q8: How can we reduce the final APK/AAB download size?
1. **Enable R8 Code & Resource Shrinking:** In `android/app/build.gradle`, ensure:
   ```groovy
   minifyEnabled true
   shrinkResources true
   ```
2. **Optimize Static Assets:** Minimize any bundled splash images or offline assets inside `www/`.
3. **Use WebP Drawables:** Convert raster PNG assets in `res/drawable` to lossless WebP format.

---

## 4. Device Testing & Debugging

### Q9: How do I test the app on a physical Android device?
1. Enable **Developer Options** on your phone (tap **Build Number** 7 times in Android Settings).
2. Enable **USB Debugging** in Developer Options.
3. Connect your phone to your computer via USB cable.
4. Verify ADB sees the device:
   ```bash
   adb devices
   ```
5. Deploy and run the app:
   ```bash
   npx cap run android
   ```

---

### Q10: Can the app work completely offline?
Because DEEN Commerce is a live e-commerce platform with real-time inventory, pricing, and payment gateways, complete offline shopping is out of scope. However, the app includes an **offline fallback screen** (`www/index.html`) that activates when network connectivity drops, offering clear diagnostics and a one-tap retry button.

---

*See also: [TROUBLESHOOTING.md](./TROUBLESHOOTING.md) for error diagnoses and [DEPLOYMENT.md](./DEPLOYMENT.md) for publishing guides.*
