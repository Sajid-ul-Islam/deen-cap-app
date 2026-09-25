*Security vulnerability reporting guidelines, disclosure SLAs, and mobile platform defense-in-depth architecture.*

# SECURITY.md — Security Policy & Vulnerability Reporting

---

## 📑 Table of Contents

- [1. Security Philosophy](#1-security-philosophy)
- [2. Supported Versions](#2-supported-versions)
- [3. Reporting a Vulnerability](#3-reporting-a-vulnerability)
- [4. Response Service Level Agreement (SLA)](#4-response-service-level-agreement-sla)
- [5. Vulnerability Scope Boundaries](#5-vulnerability-scope-boundaries)
- [6. Mobile Defense-in-Depth Architecture](#6-mobile-defense-in-depth-architecture)
  - [6.1 Keystore & Secret Management](#61-keystore--secret-management)
  - [6.2 Android WebView Hardening](#62-android-webview-hardening)
  - [6.3 Network Transport & TLS Security](#63-network-transport--tls-security)
  - [6.4 Code Obfuscation & R8 Shrinking](#64-code-obfuscation--r8-shrinking)
- [7. Coordinated Disclosure Policy](#7-coordinated-disclosure-policy)

---

## 1. Security Philosophy

DEEN Commerce is committed to safeguarding our users' personal data, payment information, and shopping privacy. We adhere to rigorous security controls across both our remote storefront infrastructure and our hybrid mobile wrapper applications.

---

## 2. Supported Versions

Security updates, patches, and hotfixes are maintained only for the currently active release branch:

| Version | Supported | Status |
| :--- | :--- | :--- |
| `0.1.x` | ✅ Yes | Current Active Release Branch |
| `< 0.1.0` | ❌ No | Deprecated / Pre-release |

---

## 3. Reporting a Vulnerability

If you discover a security vulnerability in DEEN Commerce, **please DO NOT create a public GitHub issue.** Instead, report it through our responsible disclosure channel:

- **Security Team Email:** `security@deencommerce.com` (or `[YOUR_EMAIL]`)
- **Subject Line:** `[SECURITY] Potential Vulnerability in DEEN Android App`
- **PGP Key:** *(Optional / Available upon request)*

### Required Information
To help our security team triage and resolve issues quickly, please provide:
1. Detailed description of the potential vulnerability.
2. Step-by-step reproduction instructions or a minimal Proof of Concept (PoC).
3. Affected app version(s) and Android OS version(s).
4. Potential security impact (e.g., unauthorized access, data leakage, session hijack).

---

## 4. Response Service Level Agreement (SLA)

Our security engineering team operates under the following SLA:

| Stage | SLA Timeframe | Description |
| :--- | :--- | :--- |
| **Initial Acknowledgment** | `< 48 Hours` | Confirmation that your report was received and assigned to an engineer. |
| **Triage & Assessment** | `< 7 Days` | Verification of reproducibility and initial severity scoring (CVSS). |
| **Remediation & Patch** | `< 30 Days` | Engineering fix developed, tested, and released to production. |
| **Public Disclosure** | Coordinated | Disclosure coordinated following release of the patched version. |

---

## 5. Vulnerability Scope Boundaries

### In-Scope
- Vulnerabilities within the native Android wrapper source code (`android/`).
- Capacitor bridge configuration defects exposing native APIs to unauthorized origins.
- Insecure local storage of session credentials or sensitive data on device.
- Intent spoofing, tapjacking, or unauthenticated deep-link injection.
- Insecure WebView configurations permitting cleartext transmission or unsafe file access.

### Out-of-Scope
- Vulnerabilities requiring a rooted, jailbroken, or physically compromised device with root access.
- Denial of Service (DoS) attacks against third-party content delivery networks (CDNs).
- Social engineering, phishing, or physical theft attacks against users.
- Third-party native plugin vulnerabilities where no fix is currently available upstream (though mitigations are appreciated).

---

## 6. Mobile Defense-in-Depth Architecture

### 6.1 Keystore & Secret Management
- **Zero Secrets in Git:** Signing keystores (`*.keystore`, `*.jks`), key passwords, and service account keys are strictly excluded from source control.
- **CI Secrets:** Release signing keys are injected dynamically via encrypted GitHub Repository Secrets during automated builds.

### 6.2 Android WebView Hardening
The native WebView container is hardened using the following defensive configurations:
```java
// Disable file scheme access to block local file exfiltration
webSettings.setAllowFileAccess(false);
webSettings.setAllowContentAccess(false);
webSettings.setAllowFileAccessFromFileURLs(false);
webSettings.setAllowUniversalAccessFromFileURLs(false);

// Enable Android Safe Browsing
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    webSettings.setSafeBrowsingEnabled(true);
}

// Disable WebView remote debugging in production release builds
if (!BuildConfig.DEBUG) {
    WebView.setWebContentsDebuggingEnabled(false);
}
```

### 6.3 Network Transport & TLS Security
- Cleartext HTTP traffic is strictly prohibited. `android/app/src/main/AndroidManifest.xml` enforces:
  ```xml
  android:usesCleartextTraffic="false"
  ```
- All communications are conducted over HTTPS using TLS 1.2 or TLS 1.3 with modern cipher suites.
- Certificate Pinning (HPKP / Network Security Config) is planned for high-assurance checkout flows in milestone `v0.4`.

### 6.4 Code Obfuscation & R8 Shrinking
Production release builds have R8 shrinking and code obfuscation enabled (`minifyEnabled true`, `shrinkResources true`). This strips unused classes, renames internal symbols, and hinders reverse engineering.

---

## 7. Coordinated Disclosure Policy

We believe in responsible, coordinated disclosure. We ask that researchers allow us reasonable time to remediate reported issues before publishing details to the public. In return, we commit to acknowledging researchers' contributions in our release notes (unless anonymity is requested).

---

*See also: [RULES.md](./RULES.md) for engineering standards and [ARCHITECTURE.md](./ARCHITECTURE.md) for system design.*
