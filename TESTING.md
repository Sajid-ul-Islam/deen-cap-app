*Comprehensive testing strategy, automated test commands, device compatibility matrices, and release QA checklists.*

# TESTING.md — Quality Assurance & Testing Runbook

---

## 📑 Table of Contents

- [1. Quality Philosophy & Testing Pyramid](#1-quality-philosophy--testing-pyramid)
- [2. Automated Testing Setup & Commands](#2-automated-testing-setup--commands)
- [3. Manual QA Device Matrix](#3-manual-qa-device-matrix)
- [4. Smoke Test Checklist (Mandatory Pre-Release)](#4-smoke-test-checklist-mandatory-pre-release)
- [5. Full Regression Test Matrix](#5-full-regression-test-matrix)
- [6. Performance & Memory Profiling](#6-performance--memory-profiling)
- [7. Network Simulation & Chaos Testing](#7-network-simulation--chaos-testing)
- [8. Continuous Integration & Automation](#8-continuous-integration--automation)

---

## 1. Quality Philosophy & Testing Pyramid

DEEN Commerce combines automated validation with rigorous manual device testing to ensure high stability across diverse Android hardware:

```
               /\
              /  \
             / QA \         Manual QA & Smoke Tests
            /------\        (Physical Devices, OEM Skins)
           /  E2E   \       End-to-End & Integration Tests
          /----------\      (Detox / Appium / WebdriverIO)
         / Unit & Lint\     Static Analysis, Linting & Unit Tests
        /--------------\    (Jest, TypeScript Compiler, ESLint)
```

- **Unit & Static Analysis:** Fast, automated validation of configuration, scripts, and helper functions.
- **Integration / E2E:** Verification of the Capacitor JavaScript-to-Native bridge.
- **Manual QA & Device Matrix:** Human verification across major Android OEMs (Samsung One UI, Google Pixel, Xiaomi MIUI) and screen densities.

---

## 2. Automated Testing Setup & Commands

### 2.1 Static Analysis & Linting

```bash
# Verify TypeScript strict type-checking
npx tsc --noEmit

# Run ESLint across wrapper code
npm run lint

# Check code formatting with Prettier
npx prettier --check "**/*.{ts,js,json,md}"
```

### 2.2 Native Android Compilation Check

```bash
# Clean previous builds
cd android
./gradlew clean

# Run native Android lint analysis
./gradlew lintDebug

# Verify debug compilation
./gradlew assembleDebug
```

### 2.3 Automated End-to-End Suite (Future / Roadmap)

```bash
# Execute Appium / WebdriverIO automation suite (planned for v0.3)
npm run test:e2e
```

---

## 3. Manual QA Device Matrix

Testing must cover the following matrix to account for OEM-specific WebView implementations, memory limits, and Android OS versions:

| Tier | Representative Device | OS Version | Display / Aspect | Priority |
| :--- | :--- | :--- | :--- | :--- |
| **Reference** | Google Pixel 7 / 8 | Android 14 (API 34) | 6.2" / 20:9 FHD+ | P0 (Must Pass) |
| **Primary OEM** | Samsung Galaxy S22 / S23 | Android 13 (API 33) | 6.1" / 19.5:9 FHD+ | P0 (Must Pass) |
| **Budget / Mid** | Xiaomi Redmi Note 11 / 12 | Android 12 (API 31) | 6.67" / 20:9 AMOLED | P0 (Must Pass) |
| **Legacy OS** | Samsung Galaxy A10 / S8 | Android 8.0 (API 26) | 5.8" / 18.5:9 HD+ | P1 (Required) |
| **Foldable / Large**| Samsung Galaxy Z Fold 5 | Android 14 (API 34) | 7.6" Unfolded Tablet | P2 (Recommended) |

---

## 4. Smoke Test Checklist (Mandatory Pre-Release)

Execute this 10-point test checklist on at least two physical devices before signing off on any production build:

| # | Step / Action | Expected Result | Pass/Fail |
| :-: | :--- | :--- | :---: |
| **1** | **Cold Launch:** Tap app icon from home screen. | Branded splash screen shows immediately; transitions smoothly to `https://deencommerce.com/` within 3s. | [ ] |
| **2** | **Storefront Render:** Scroll homepage top to bottom. | Layouts, product carousels, and images render crisply without visual artifacts or clipping. | [ ] |
| **3** | **Search & Catalog:** Search for a product (e.g., "Thobe"). | Search results populate quickly; filters and sorting operate properly. | [ ] |
| **4** | **Product Detail:** Tap a product card. | Product page loads; image zoom, variant picker (color/size), and price display accurately. | [ ] |
| **5** | **Add to Cart:** Add product to shopping bag. | Cart counter increments; feedback toast/drawer appears confirming addition. | [ ] |
| **6** | **Authentication:** Log in with test user credentials. | User account dashboard displays; session cookie is saved. | [ ] |
| **7** | **Checkout Navigation:** Proceed to checkout screen. | Checkout form opens; address entry and payment gateway selection load over secure HTTPS. | [ ] |
| **8** | **Hardware Back Button:** Press back button on product page. | Navigates back to search/catalog. Pressing back from home displays exit confirmation toast. | [ ] |
| **9** | **Background & Resume:** Press home button, wait 30s, reopen. | App restores state instantly without white flash or session drop. | [ ] |
| **10** | **Process Kill & Restart:** Swipe app away in recents, relaunch. | App boots cleanly; user remains logged in; cart items persist. | [ ] |

---

## 5. Full Regression Test Matrix

In addition to smoke tests, full regression runs prior to major version releases must validate:

- **Orientation Changes:** Rotate device from Portrait to Landscape and back during checkout and product browsing. UI must reflow without loss of form input.
- **External Link Traversal:** Click social links (Instagram, Facebook) or customer support chat. Ensure links open outside the core app without hijacking the WebView.
- **Font & Display Zoom:** Increase Android system font size to "Largest". Ensure text remains readable and buttons do not clip text.
- **Soft Keyboard Handling:** Tap into search bars and address fields. Verify soft keyboard slides up smoothly without obscuring the active input field.

---

## 6. Performance & Memory Profiling

Use Android Studio Profiler during manual testing sessions:

1. **CPU Usage:** CPU utilization should remain under **`15%`** during passive reading and under **`45%`** during fast flings/scrolling.
2. **Memory Leaks:** Navigate across 20 product pages and return to home. Verify WebView memory garbage collects cleanly, maintaining RSS under **`300 MB`**.
3. **Network Overhead:** Verify image lazy-loading is active; only visible images are fetched over the network.

---

## 7. Network Simulation & Chaos Testing

Test application resilience using Android Emulator Network Speed settings:

| Profile | Latency / Bandwidth | Expected Behavior |
| :--- | :--- | :--- |
| **Full 4G / Wi-Fi** | Full speed, `< 50ms` | Instant loading, high-res images stream immediately. |
| **Edge / 2G Throttle**| `300ms` latency, `250kbps` | Skeleton loaders appear; UI remains responsive during download. |
| **Airplane Mode** | No connection | Displays local branded offline screen (`www/index.html`) with "Try Again" CTA. |
| **Network Toggle** | Toggle Airplane Mode off | Tapping "Try Again" detects active connection and reloads live store. |

---

## 8. Continuous Integration & Automation

Every pull request triggers our GitHub Actions CI pipeline:
- Validates repository hygiene and static analysis.
- Builds the native Android debug APK using `./gradlew assembleDebug`.
- Archives the resulting APK artifact for immediate tester download.

For CI configuration details, see [.github/workflows/android-build.yml](./.github/workflows/android-build.yml).

---

*See also: [TROUBLESHOOTING.md](./TROUBLESHOOTING.md) for debugging test failures and [DEPLOYMENT.md](./DEPLOYMENT.md) for release workflows.*
