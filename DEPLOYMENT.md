*Production release engineering, Android App Bundle generation, cryptographic signing, and Google Play Store publishing workflows.*

# DEPLOYMENT.md — Production Build & Deployment Guide

---

## 📑 Table of Contents

- [1. Build Variants Overview](#1-build-variants-overview)
- [2. Release Keystore Generation & Secret Management](#2-release-keystore-generation--secret-management)
- [3. Gradle Signing Configuration](#3-gradle-signing-configuration)
- [4. Building Production Artifacts (AAB & APK)](#4-building-production-artifacts-aab--apk)
- [5. Version Governance (versionCode & versionName)](#5-version-governance-versioncode--versionname)
- [6. Google Play Console Publishing Runbook](#6-google-play-console-publishing-runbook)
- [7. Production Release Checklist](#7-production-release-checklist)
- [8. Incident Mitigation & Rollback Strategy](#8-incident-mitigation--rollback-strategy)
- [9. Over-The-Air (OTA) Updates Architecture](#9-over-the-air-ota-updates-architecture)

---

## 1. Build Variants Overview

The Android Gradle configuration provides two standard build variants:

| Variant | Purpose | Optimization | Keystore | Output Location |
| :--- | :--- | :--- | :--- | :--- |
| **`debug`** | Local testing & developer iteration | No shrinking, logs enabled, WebView inspectable | Android debug keystore | `android/app/build/outputs/apk/debug/app-debug.apk` |
| **`release`** | Google Play Store distribution | R8 code shrinking, resource minification, ProGuard | Official production release keystore | `android/app/build/outputs/bundle/release/app-release.aab` |

---

## 2. Release Keystore Generation & Secret Management

A production Java Keystore (JKS) is required to cryptographically sign Android binaries for Google Play.

### 2.1 Generating a New Keystore

Run the following command in a secure, private terminal:

```bash
keytool -genkeypair -v \
  -keystore deen-commerce-release.keystore \
  -alias deencommerce \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000
```

> [!CAUTION]
> **CRITICAL SECURITY WARNING:**  
> Never commit `deen-commerce-release.keystore` to git. Back up the keystore and its passphrases in a secure enterprise password vault (e.g., 1Password, Bitwarden, or AWS Secrets Manager). If you lose this keystore, you cannot update the existing app on Google Play without contacting Google Developer Support.

### 2.2 Configuring `key.properties`

Create a local configuration file at `android/key.properties` (this file is excluded by `.gitignore`):

```properties
storePassword=YOUR_SECURE_STORE_PASSWORD
keyPassword=YOUR_SECURE_KEY_PASSWORD
keyAlias=deencommerce
storeFile=/absolute/path/to/deen-commerce-release.keystore
```

---

## 3. Gradle Signing Configuration

In `android/app/build.gradle`, load the properties file securely:

```groovy
def keystorePropertiesFile = rootProject.file("key.properties")
def keystoreProperties = new Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(new FileInputStream(keystorePropertiesFile))
}

android {
    signingConfigs {
        release {
            if (keystorePropertiesFile.exists()) {
                storeFile file(keystoreProperties['storeFile'])
                storePassword keystoreProperties['storePassword']
                keyAlias keystoreProperties['keyAlias']
                keyPassword keystoreProperties['keyPassword']
            }
        }
    }

    buildTypes {
        release {
            signingConfig signingConfigs.release
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}
```

---

## 4. Building Production Artifacts (AAB & APK)

### 4.1 Sync Assets First

Always synchronize the web assets and native bindings before building:

```bash
# From project root
npm run build
npx cap sync android
```

### 4.2 Build Android App Bundle (AAB) for Google Play

Google Play requires the **AAB** format for all new app releases:

```bash
cd android
./gradlew bundleRelease
```

The resulting bundle is written to:
`android/app/build/outputs/bundle/release/app-release.aab`

### 4.3 Build Universal Release APK (for Testing / Ad-Hoc Distribution)

```bash
cd android
./gradlew assembleRelease
```

The resulting signed APK is located at:
`android/app/build/outputs/apk/release/app-release.apk`

---

## 5. Version Governance (`versionCode` & `versionName`)

Before every release, update the version identifiers in `android/app/build.gradle`:

```groovy
defaultConfig {
    applicationId "com.deencommerce.app"
    minSdkVersion rootProject.ext.minSdkVersion      // 26
    targetSdkVersion rootProject.ext.targetSdkVersion // 34
    versionCode 10001                                // Monotonically increasing integer
    versionName "0.1.0"                              // SemVer matching package.json
}
```

### Rules:
- `versionCode` **MUST** be incremented for every Google Play upload (e.g., `10000` -> `10001` -> `10002`). Google Play Console rejects uploads with duplicate or lower `versionCode`.
- `versionName` **MUST** match the version in `package.json` and follow Semantic Versioning (`MAJOR.MINOR.PATCH`).

---

## 6. Google Play Console Publishing Runbook

1. **Log in to Play Console:** Access the [Google Play Console](https://play.google.com/console) with authorized publisher credentials.
2. **Select Application:** Click on **DEEN Commerce** (`com.deencommerce.app`).
3. **Internal Testing Track First:**
   - Navigate to **Testing > Internal testing**.
   - Click **Create new release**.
   - Upload `app-release.aab`.
   - Enter release notes describing changes.
   - Click **Review release** and **Start rollout to Internal testing**.
4. **Internal QA Validation:** Verify the build installs cleanly from the Google Play Store on internal tester devices.
5. **Promote to Closed / Open Beta:** Once validated internally, promote the release to the Closed Alpha or Open Beta track.
6. **Promote to Production:** Once testing sign-off is complete, promote the release to **Production** with a staged rollout (recommended: 10% -> 25% -> 50% -> 100% over 5 days).

---

## 7. Production Release Checklist

Complete before promoting any build to the Production Track:

- [ ] All CI builds passed on `main`.
- [ ] `versionCode` and `versionName` bumped in `android/app/build.gradle`.
- [ ] `package.json` version matches `versionName`.
- [ ] [CHANGELOG.md](./CHANGELOG.md) updated with release notes and date.
- [ ] 10-point Smoke Test checklist completed on physical hardware.
- [ ] `cleartextTrafficPermitted="false"` confirmed in release manifest.
- [ ] WebView debugging verified disabled on release build.
- [ ] Google Play Store assets updated (screenshots, promotional copy if needed).
- [ ] Privacy Policy URL active and verified at `https://deencommerce.com/privacy-policy`.

---

## 8. Incident Mitigation & Rollback Strategy

Google Play Console does not provide a one-click binary rollback mechanism. Follow this mitigation protocol in case of critical bugs:

1. **Halt Active Rollout:** If a staged rollout is active, immediately visit **Release > Production**, click **Halt rollout**. This stops new users from receiving the buggy update.
2. **Identify Severity:**
   - **Web/Storefront Issue:** If the bug is located in the web application (`deencommerce.com`), deploy a hotfix directly to the web server. The app loads remote content dynamically, resolving the issue for all mobile users instantly without an app update!
   - **Native Wrapper Crash:** If the crash is inside the Android native shell, proceed to Step 3.
3. **Hotfix Binary Release:**
   - Create a hotfix branch: `fix/hotfix-v0.1.1`.
   - Patch the defect.
   - Increment `versionCode` and bump `versionName` (e.g. `0.1.1`).
   - Run full build and sign new AAB.
   - Upload directly to Production track with 100% rollout and mark as expedited review.

---

## 9. Over-The-Air (OTA) Updates Architecture

For future milestones, DEEN Commerce plans to integrate `@capgo/capacitor-updater`. This will enable live instant updates for local shell assets (`www/`) without requiring Google Play Store binary reviews:

```text
[Web / Fallback Fix]
         │
         ▼
[GitHub Actions OTA Deploy]
         │
         ▼
[Capacitor Updater Cloud Channel]
         │
         ▼ (Background sync on app launch)
[Client Device Installs New Shell Bundle]
```

---

*See also: [TESTING.md](./TESTING.md) for pre-release test procedures and [ENV.md](./ENV.md) for environment secrets.*
