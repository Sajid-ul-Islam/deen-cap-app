*Chronological record of notable changes, releases, and security patches for the DEEN Commerce mobile application.*

# CHANGELOG.md — Release History

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]

### Added
- Native offline fallback screen integration inside `www/index.html`.
- Network connectivity listener using `@capacitor/network` to notify web runtime.
- Deep link intent filter templates for `https://deencommerce.com/product/*`.
- Push notification registration scaffolding for Firebase Cloud Messaging (FCM).

### Changed
- Refactored hardware back button handler to support multi-layer modal dismissal.

### Security
- Verified ProGuard / R8 code shrinking rules for release builds.

---

## [0.1.0] - 2026-09-25

### Added
- **Initial Capacitor 6 Native Android Wrapper:** Core native shell wrapping `https://deencommerce.com/`.
- **Remote Storefront Loading:** Secure WebView container configured to stream live production storefront.
- **Hardware Back Navigation:** Integrated `@capacitor/app` back-button event listener to traverse WebView history.
- **Branded Splash Screen & Adaptive Icon:** Native Android 12+ adaptive icon and splash screen featuring DEEN green (`#0A4D3C`) and gold branding.
- **Session Persistence:** Configured Android `CookieManager` to persist user authentication cookies across app launches.
- **Continuous Integration Pipeline:** GitHub Actions workflow (`android-build.yml`) for automated debug APK compilation and artifact archiving.
- **Complete Technical Documentation Suite:** Production-grade documentation including `README.md`, `AGENTS.md`, `RULES.md`, `SPECS.md`, `DESIGN.md`, `ARCHITECTURE.md`, `SECURITY.md`, and operational guides.

### Security
- Explicitly disabled cleartext HTTP traffic in production manifest (`usesCleartextTraffic="false"`).
- Restricted WebView debugging strictly to debug build configurations.

---

*For release signing and store publishing guidelines, see [DEPLOYMENT.md](./DEPLOYMENT.md).*
