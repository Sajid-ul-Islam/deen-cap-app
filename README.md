*Production-grade documentation and operational guide for the DEEN Commerce Capacitor Android mobile application.*

# DEEN Commerce — Android Mobile Application

![Build Status](https://img.shields.io/badge/build-passing-brightgreen?style=flat-square)
![Platform](https://img.shields.io/badge/platform-Android%20(API%2026%2B)-blue?style=flat-square)
![Capacitor](https://img.shields.io/badge/Capacitor-v6.0%2B-119EFF?style=flat-square)
![License](https://img.shields.io/badge/license-MIT-green?style=flat-square)
![Version](https://img.shields.io/badge/version-0.1.0-orange?style=flat-square)

---

## 📖 Overview

**DEEN Commerce** (`com.deencommerce.app`) is a high-performance hybrid mobile application engineered using **Capacitor 6+** to package the e-commerce platform [https://deencommerce.com/](https://deencommerce.com/) into a native Android experience.

By wrapping the remote e-commerce storefront in a hardened native Android WebView container, the application delivers smooth mobile commerce capabilities while laying the foundation for native device features including push notifications, biometric authentication, offline fallback handling, and native checkout integrations.

```
+--------------------------------------------------------------+
|                    DEEN Commerce Android App                 |
|                                                              |
|   +------------------------------------------------------+   |
|   |         Capacitor 6 Native Android Shell             |   |
|   |  - Hardware Back Button      - Splash Screen         |   |
|   |  - Native Status/Nav Bars   - Network State Listener |   |
|   +------------------------------------------------------+   |
|                              |                               |
|   +------------------------------------------------------+   |
|   |               Hardened Android WebView               |   |
|   |  - Session Persistence      - Hardware Acceleration  |   |
|   +------------------------------------------------------+   |
|                              |                               |
|   +------------------------------------------------------+   |
|   |         Remote Storefront (HTTPS / TLS 1.3)          |   |
|   |              https://deencommerce.com/               |   |
|   +------------------------------------------------------+   |
+--------------------------------------------------------------+
```

---

## 📑 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [Prerequisites](#-prerequisites)
- [Quick Start](#-quick-start)
- [Project Directory Structure](#-project-directory-structure)
- [Available Scripts](#-available-scripts)
- [Documentation Index](#-documentation-index)
- [Security & Compliance](#-security--compliance)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Key Features

- **Native WebView Shell:** Optimized WebView container loading `https://deencommerce.com/` with hardware acceleration enabled.
- **Hardware Back Button Navigation:** Intercepts Android hardware and gesture back navigation to traverse web browsing history before prompting exit.
- **Branded Splash & Adaptive Icon:** Native Android 12+ adaptive icon and branded splash screen using the DEEN Commerce visual identity.
- **Session & Cookie Persistence:** Native cookie management ensuring login sessions and cart states persist across app closures.
- **Network Resilience:** Instant detection of offline states with auto-retry mechanisms.
- **Security Hardening:** Enforced HTTPS with cleartext traffic disabled in release builds.
- **Extensible Architecture:** Designed for upcoming native modules:
  - 🔔 Firebase Cloud Messaging (FCM) push notifications
  - 🔒 Biometric authentication (Face & Fingerprint)
  - 💳 Native Google Pay integration
  - 📷 Camera access for customer reviews

---

## 🛠️ Prerequisites

Before building or running the project locally, ensure you have installed:

| Tool | Version Requirement | Purpose |
| :--- | :--- | :--- |
| **Node.js** | `>= 18.18.0` (LTS recommended) | JavaScript runtime & tooling |
| **npm** | `>= 9.0.0` | Package management |
| **Java Development Kit (JDK)** | `17` (Eclipse Temurin / OpenJDK 17) | Gradle Android compilation |
| **Android Studio** | `Hedgehog (2023.1.1)+` | Android IDE, SDK, & Emulators |
| **Android SDK** | `API 34` (Android 14) platform & tools | Target SDK compilation |
| **Android Min SDK** | `API 26` (Android 8.0 Oreo) | Minimum supported Android OS |

---

## 🚀 Quick Start

Follow these steps to get a local development build running on an Android emulator or connected device:

### 1. Clone the Repository

```bash
git clone https://github.com/deencommerce/deen-cap.git
cd deen-cap
```

### 2. Install Node Dependencies

```bash
npm install
```

### 3. Synchronize Capacitor Native Android Project

```bash
npx cap sync android
```

### 4. Run the Application

You can launch directly via the Capacitor CLI:

```bash
# Detects connected USB device or active emulator
npx cap run android
```

Or open the project inside Android Studio:

```bash
npx cap open android
```

From Android Studio, click **Run 'app'** (`Shift + F10`) to deploy to your selected device or emulator.

---

## 📁 Project Directory Structure

```text
deen-cap/
├── .cursorrules                         # AI coding rules for Cursor IDE
├── .github/                             # GitHub automation and templates
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md                # Standardized bug reporting template
│   │   └── feature_request.md           # Feature proposal template
│   ├── PULL_REQUEST_TEMPLATE.md         # PR submission checklist
│   └── workflows/
│       └── android-build.yml            # CI build & APK artifact workflow
├── android/                             # Native Android Studio project (Gradle)
│   ├── app/
│   │   ├── build.gradle                 # App-level build and dependencies
│   │   └── src/main/
│   │       ├── AndroidManifest.xml      # Permissions and intent filters
│   │       ├── assets/                  # Bundled assets (public webDir)
│   │       ├── java/com/deencommerce/app/
│   │       │   └── MainActivity.java    # Native entry point
│   │       └── res/                     # Splash, icons, and layout drawables
│   ├── build.gradle                     # Top-level Gradle configuration
│   └── gradle.properties                # JVM memory and build flags
├── docs/                                # Detailed technical documentation
├── src/                                 # Optional frontend wrapper scripts
├── www/                                 # Static fallback assets (local shell)
│   ├── index.html                       # Fallback loading / offline shell
│   └── favicon.ico                      # Brand icon
├── capacitor.config.ts                  # Central Capacitor configuration
├── package.json                         # Project dependencies and npm scripts
├── tsconfig.json                        # TypeScript compiler configuration
├── README.md                            # Main project overview (this file)
├── AGENTS.md                            # Guidelines for autonomous AI coding agents
├── RULES.md                             # Non-negotiable engineering rules
├── SPECS.md                             # Functional & non-functional specifications
├── DESIGN.md                            # Visual design system and UX specs
├── ARCHITECTURE.md                      # System architecture & bridge data flows
├── CONTRIBUTING.md                      # Contribution guide & PR lifecycle
├── CHANGELOG.md                         # Release history & version notes
├── SECURITY.md                          # Vulnerability reporting & hardening
├── CODE_OF_CONDUCT.md                   # Community standards (Contributor Covenant)
├── LICENSE.md                           # MIT License terms
├── ROADMAP.md                           # Product milestones & feature timeline
├── TESTING.md                           # Testing matrix, smoke tests, and QA
├── DEPLOYMENT.md                        # Keystore signing, AAB, and Play Store guide
├── ENV.md                               # Environment variable reference
├── FAQ.md                               # Frequently asked questions
└── TROUBLESHOOTING.md                   # Diagnostic steps for common issues
```

---

## 📜 Available Scripts

| Command | Action |
| :--- | :--- |
| `npm run build` | Compiles web assets into `www/` |
| `npx cap sync android` | Copies web assets and updates Android plugins/dependencies |
| `npx cap copy android` | Copies web assets without updating native plugins |
| `npx cap update android` | Updates native plugin bindings in the Android project |
| `npx cap open android` | Opens the `android/` directory in Android Studio |
| `npx cap run android` | Builds and deploys debug APK to connected device |
| `cd android && ./gradlew assembleDebug` | Builds a standalone debug APK (`app-debug.apk`) |
| `cd android && ./gradlew bundleRelease` | Builds a signed production Android App Bundle (`app-release.aab`) |

---

## 📚 Documentation Index

For comprehensive details on development, design, and operations, refer to:

- 🤖 [AGENTS.md](./AGENTS.md) — Operational instructions for AI agents.
- 📏 [RULES.md](./RULES.md) — Engineering guidelines and code conventions.
- 📋 [SPECS.md](./SPECS.md) — Technical specifications and acceptance criteria.
- 🎨 [DESIGN.md](./DESIGN.md) — Design tokens, typography, and UI specs.
- 🏗️ [ARCHITECTURE.md](./ARCHITECTURE.md) — Data flows and WebView bridge architecture.
- 🤝 [CONTRIBUTING.md](./CONTRIBUTING.md) — Setup guide and PR workflows.
- 🛡️ [SECURITY.md](./SECURITY.md) — Security policies and vulnerability disclosure.
- 🧪 [TESTING.md](./TESTING.md) — QA checklist, smoke tests, and device matrix.
- 🚀 [DEPLOYMENT.md](./DEPLOYMENT.md) — Release signing and Google Play publishing.
- 🔧 [TROUBLESHOOTING.md](./TROUBLESHOOTING.md) — Diagnostic and debugging solutions.

---

## 🔒 Security & Compliance

The application enforces strict transport security and safe WebView practices:
- **HTTPS Only:** Cleartext HTTP traffic is disabled in production builds.
- **Safe Browsing:** Android WebView Safe Browsing API is enabled.
- **Zero Credentials:** Secrets, signing keys, and passwords must never be committed to git.

For reporting vulnerabilities, consult [SECURITY.md](./SECURITY.md).

---

## 🤝 Contributing

We welcome contributions from the community and internal engineering teams. Please review [CONTRIBUTING.md](./CONTRIBUTING.md) and adhere to [CODE_OF_CONDUCT.md](./CODE_OF_CONDUCT.md) before submitting pull requests.

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE.md](./LICENSE.md) file for details.
