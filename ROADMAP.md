*Strategic milestone schedule, feature evolution, and technical roadmap for DEEN Commerce mobile applications.*

# ROADMAP.md — Product & Technical Roadmap

---

## 📑 Table of Contents

- [1. Product Vision & Phasing Overview](#1-product-vision--phasing-overview)
- [2. Milestone Timeline](#2-milestone-timeline)
  - [v0.1: MVP WebView Wrapper (Current)](#v01-mvp-webview-wrapper-current)
  - [v0.2: Engagement & Offline Reliability](#v02-engagement--offline-reliability)
  - [v0.3: Deep Linking & Seamless Authentication](#v03-deep-linking--seamless-authentication)
  - [v0.4: Native Commerce & Multimedia](#v04-native-commerce--multimedia)
  - [v1.0: Public Production Release & Observability](#v10-public-production-release--observability)
  - [Future Horizons (v1.1+)](#future-horizons-v11)
- [3. Feature Matrix & Delivery Status](#3-feature-matrix--delivery-status)
- [4. Governance & Roadmap Updates](#4-governance--roadmap-updates)

---

## 1. Product Vision & Phasing Overview

The roadmap balances immediate speed to market with progressive enhancement toward a rich, native mobile experience. By beginning with an optimized Capacitor 6 WebView wrapper around [https://deencommerce.com/](https://deencommerce.com/), we provide an app store presence while continuously unbundling critical journeys (authentication, notifications, payments) into native Android device integrations.

---

## 2. Milestone Timeline

### v0.1: MVP WebView Wrapper (Current)
*Target: Q3 2026 — Status: Completed ✅*
- [x] Capacitor 6 native Android shell setup (`com.deencommerce.app`).
- [x] Remote storefront loading for `https://deencommerce.com/`.
- [x] Branded splash screen and Android 12+ adaptive app icon.
- [x] Hardware back-button event listener via `@capacitor/app`.
- [x] Android `CookieManager` session persistence across app restarts.
- [x] External link interception using default browser / InAppBrowser.
- [x] Automated GitHub Actions CI workflow for debug APK builds.
- [x] Complete production-grade engineering documentation.

---

### v0.2: Engagement & Offline Reliability
*Target: Q4 2026 — Status: In Development 🚀*
- [ ] **Firebase Cloud Messaging (FCM):** Push notification integration for order status updates and flash promotional alerts.
- [ ] **Local Offline Fallback Shell:** Interactive offline screen (`www/index.html`) with network listener (`@capacitor/network`) and automated reconnection.
- [ ] **Native Status Bar & Navigation Bar Theming:** Dynamic color synchronization with DEEN green (`#0A4D3C`).
- [ ] **Pull-to-Refresh:** Native swipe-down gesture to trigger WebView reload.
- [ ] **In-App Rating Prompt:** Google Play Core in-app review API prompt for engaged shoppers.

---

### v0.3: Deep Linking & Seamless Authentication
*Target: Q1 2027 — Status: Planned 📋*
- [ ] **Android App Links:** Verified digital asset links (`assetlinks.json`) enabling direct routing to `deencommerce.com/product/*`.
- [ ] **Biometric Authentication:** Fingerprint and Face Unlock via Android `BiometricPrompt` for instant login and stored payment protection.
- [ ] **Secure Credential Storage:** Native Android Keystore integration for cryptographic token caching.
- [ ] **Social Sign-In Native Bridge:** Google One Tap authentication bridge.

---

### v0.4: Native Commerce & Multimedia
*Target: Q2 2027 — Status: Planned 📋*
- [ ] **Native Google Pay Integration:** Fast 1-tap checkout via Google Pay API without manual credit card entry.
- [ ] **Camera & Gallery Review Uploads:** Native camera capture bridge (`@capacitor/camera`) allowing shoppers to upload photo/video product reviews.
- [ ] **Haptic Feedback:** Subtle vibration cues for successful cart additions and checkout actions.
- [ ] **Share Sheet Integration:** Android native share intent for products, wishlists, and referral codes.

---

### v1.0: Public Production Release & Observability
*Target: Q3 2027 — Status: Planned 📋*
- [ ] **Google Play Store Launch:** Full production rollout across primary target geographies.
- [ ] **Crash Reporting & Observability:** Sentry SDK integration for real-time mobile crash tracking and breadcrumbs.
- [ ] **Mobile Analytics:** Firebase Analytics & Google Analytics 4 (GA4) event streaming.
- [ ] **A/B Testing:** Firebase Remote Config for testing promotional banners and navigation layouts.
- [ ] **Over-The-Air (OTA) Updates:** Live shell asset updating via `@capgo/capacitor-updater`.

---

### Future Horizons (v1.1+)
- **Cross-Platform Expansion:** iOS application deployment via Capacitor iOS runtime.
- **Tablet & Foldable Optimizations:** Split-pane catalog and cart views for Samsung Galaxy Fold and Android tablets.
- **Wear OS Companion:** Watch glance widgets for order delivery status and prayer time notifications.

---

## 3. Feature Matrix & Delivery Status

| Feature Module | Milestone | Native Plugin / API | Complexity | Status |
| :--- | :--- | :--- | :--- | :--- |
| **WebView Core Wrapper** | v0.1 | Android System WebView | Medium | ✅ Complete |
| **Back Button Intercept** | v0.1 | `@capacitor/app` | Low | ✅ Complete |
| **Branded Splash & Icon** | v0.1 | Android Adaptive Icons | Low | ✅ Complete |
| **Push Notifications** | v0.2 | `@capacitor/push-notifications` | Medium | 🚀 In Progress |
| **Offline Fallback** | v0.2 | `@capacitor/network` + `www/` | Medium | 🚀 In Progress |
| **Android App Links** | v0.3 | Android Intent Filters | Medium | 📋 Planned |
| **Biometric Authentication** | v0.3 | `@pantrist/capacitor-biometrics` | High | 📋 Planned |
| **Google Pay Native** | v0.4 | Google Pay API | High | 📋 Planned |
| **Camera Review Upload** | v0.4 | `@capacitor/camera` | Low | 📋 Planned |
| **Sentry Crash Tracking** | v1.0 | `@sentry/capacitor` | Medium | 📋 Planned |
| **iOS Platform Port** | Future | Capacitor iOS Engine | High | 📋 Planned |

---

## 4. Governance & Roadmap Updates

This roadmap is maintained by the core mobile engineering team and product managers. Quarterly reviews adjust milestone priorities based on shopper conversion data, user feedback, and Google Play Store performance.

---

*See also: [SPECS.md](./SPECS.md) for detailed requirements and [DEPLOYMENT.md](./DEPLOYMENT.md) for production release procedures.*
