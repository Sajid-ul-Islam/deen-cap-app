*Environment variable specifications, configuration matrices, and build-time secret isolation guidelines.*

# ENV.md — Environment Configuration & Secrets Management

---

## 📑 Table of Contents

- [1. Configuration Philosophy](#1-configuration-philosophy)
- [2. Environment Variables Matrix](#2-environment-variables-matrix)
- [3. Build-Time vs. Runtime Variables in Capacitor](#3-build-time-vs-runtime-variables-in-capacitor)
- [4. Local Setup with `.env.example`](#4-local-setup-with-envexample)
- [5. CI/CD Secrets Injection](#5-cicd-secrets-injection)
- [6. Secrets Hygiene & Version Control Policy](#6-secrets-hygiene--version-control-policy)

---

## 1. Configuration Philosophy

DEEN Commerce uses environment-based configuration to decouple the native application binary from target backend URLs and diagnostic flags. This enables seamless switching between local development emulators, staging environments, and production servers.

---

## 2. Environment Variables Matrix

| Variable Name | Type | Description | Development | Staging | Production |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `CAPACITOR_SERVER_URL` | String (URL) | Remote storefront URL loaded by WebView | `http://10.0.2.2:3000` | `https://staging.deencommerce.com/` | `https://deencommerce.com/` |
| `CAPACITOR_APP_ID` | String | Android application package identifier | `com.deencommerce.app.dev` | `com.deencommerce.app.staging` | `com.deencommerce.app` |
| `CAPACITOR_APP_NAME` | String | App title displayed on Android launcher | `DEEN Dev` | `DEEN Staging` | `DEEN Commerce` |
| `ENABLE_WEBVIEW_DEBUG` | Boolean | Allows Chrome DevTools inspect connection | `true` | `true` | `false` |
| `SENTRY_DSN` | String (URL) | Sentry crash reporting telemetry endpoint | `""` (disabled) | `https://key@sentry.io/staging` | `https://key@sentry.io/prod` |
| `FCM_SENDER_ID` | String | Firebase Cloud Messaging Project Sender ID | `1234567890` | `1234567890` | `TODO: [PROD_FCM_ID]` |

---

## 3. Build-Time vs. Runtime Variables in Capacitor

Because Capacitor bundles native configuration files during the compilation phase, environment variables are classified into two categories:

### 3.1 Build-Time Variables
- **Consumed by:** `capacitor.config.ts`, Gradle build scripts, and native Android resources (`res/values/strings.xml`).
- **Mechanism:** Evaluated when running `npx cap sync android` or `./gradlew assembleRelease`.
- **Example:** `CAPACITOR_SERVER_URL` dictates what `server.url` is serialized into `android/app/src/main/assets/capacitor.config.json`.

```typescript
// capacitor.config.ts
import { CapacitorConfig } from '@capacitor/cli';
import dotenv from 'dotenv';

dotenv.config();

const config: CapacitorConfig = {
  appId: process.env.CAPACITOR_APP_ID || 'com.deencommerce.app',
  appName: process.env.CAPACITOR_APP_NAME || 'DEEN Commerce',
  webDir: 'www',
  server: {
    url: process.env.CAPACITOR_SERVER_URL || 'https://deencommerce.com/',
    cleartext: process.env.NODE_ENV !== 'production',
  },
};

export default config;
```

### 3.2 Runtime Variables
- **Consumed by:** Web storefront scripts running inside the WebView.
- **Mechanism:** Injected dynamically by the remote web server during HTML document generation or retrieved via `/api/config`.
- **Note:** Never embed private API keys in client-side runtime variables.

---

## 4. Local Setup with `.env.example`

All supported environment variables are template-documented in `.env.example`.

### Local Developer Quick Setup:
```bash
# Copy the template to your local environment file
cp .env.example .env

# Edit .env with your specific local development values
nano .env

# Synchronize Capacitor to apply the updated configuration
npx cap sync android
```

### Example `.env.example` Content:
```bash
# Capacitor Android Configuration Template
NODE_ENV=development
CAPACITOR_SERVER_URL=https://deencommerce.com/
CAPACITOR_APP_ID=com.deencommerce.app
CAPACITOR_APP_NAME=DEEN Commerce
ENABLE_WEBVIEW_DEBUG=true

# Observability (Optional in local dev)
SENTRY_DSN=
FCM_SENDER_ID=
```

---

## 5. CI/CD Secrets Injection

In GitHub Actions (`.github/workflows/android-build.yml`), production secrets are securely mapped using GitHub Repository Secrets:

```yaml
env:
  CAPACITOR_SERVER_URL: ${{ secrets.CAPACITOR_SERVER_URL }}
  KEYSTORE_BASE64: ${{ secrets.ANDROID_KEYSTORE_BASE64 }}
  KEYSTORE_PASSWORD: ${{ secrets.KEYSTORE_PASSWORD }}
  KEY_ALIAS: ${{ secrets.KEY_ALIAS }}
  KEY_PASSWORD: ${{ secrets.KEY_PASSWORD }}
```

---

## 6. Secrets Hygiene & Version Control Policy

- **NEVER Commit `.env`:** Ensure `.env` is listed in `.gitignore`.
- **Commit `.env.example` Only:** When a new environment variable is introduced, immediately update `.env.example` and this document (`ENV.md`).
- **Gitleaks Pre-Commit Hook:** Maintainers recommend installing `gitleaks` to block inadvertent commits of credentials or tokens.

---

*See also: [DEPLOYMENT.md](./DEPLOYMENT.md) for production signing configuration and [RULES.md](./RULES.md) for secrets compliance.*
