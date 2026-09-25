*Mandatory engineering standards, operational constraints, and quality policies for human developers and automated agents.*

# RULES.md — Engineering Standards & Development Policies

---

## 📑 Table of Contents

- [1. Universal Policy & RFC 2119 Conformance](#1-universal-policy--rfc-2119-conformance)
- [2. Version Control & Git Protocols](#2-version-control--git-protocols)
- [3. Code Standards & Static Analysis](#3-code-standards--static-analysis)
- [4. Security, Secrets & Privacy Standards](#4-security-secrets--privacy-standards)
- [5. Capacitor & Native Platform Rules](#5-capacitor--native-platform-rules)
- [6. Android Permissions & Manifest Governance](#6-android-permissions--manifest-governance)
- [7. Versioning & Release Governance](#7-versioning--release-governance)
- [8. Pull Request & Code Review Acceptance](#8-pull-request--code-review-acceptance)

---

## 1. Universal Policy & RFC 2119 Conformance

The key words **"MUST"**, **"MUST NOT"**, **"REQUIRED"**, **"SHALL"**, **"SHALL NOT"**, **"SHOULD"**, **"SHOULD NOT"**, **"RECOMMENDED"**, **"MAY"**, and **"OPTIONAL"** in this document are to be interpreted as described in [RFC 2119](https://www.ietf.org/rfc/rfc2119.txt).

Every contributor—whether human engineer or automated AI agent—**MUST** strictly comply with these rules. Violations will result in rejected pull requests and failed CI checks.

---

## 2. Version Control & Git Protocols

### 2.1 Branching Model

- Direct commits to `main` **MUST NOT** occur under any circumstances. All modifications **MUST** flow through a pull request.
- Branch names **MUST** follow strict hierarchical naming prefixes:
  - `feat/<feature-description>`: New functional features or native plugins
  - `fix/<issue-description>`: Bug fixes and defect corrections
  - `chore/<task-description>`: Dependency updates, build tooling, or cleanup
  - `docs/<documentation-scope>`: Documentation updates or additions
  - `refactor/<refactor-scope>`: Code restructuring without functional alterations
  - `test/<test-scope>`: Test additions or test framework adjustments

### 2.2 Commit Message Standard

All commit messages **MUST** adhere to the [Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/) specification:

```text
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

Allowed types:
- `feat`: A new user-facing or technical capability
- `fix`: A patch or bug resolution
- `docs`: Documentation only changes
- `style`: Formatting, missing semi-colons, whitespace
- `refactor`: A code change that neither fixes a bug nor adds a feature
- `perf`: A code change that improves runtime performance
- `test`: Adding missing tests or correcting existing tests
- `chore`: Changes to build process, tooling, or package configurations

#### Examples

**Compliant Commit Messages:**
```bash
feat(bridge): implement hardware back button handler
fix(webview): resolve session cookie drop on app resume
docs(readme): add Android Studio Hedgehog setup instructions
chore(deps): update @capacitor/cli to v6.1.0
```

**Non-Compliant Commit Messages:**
```bash
fixed bug
update
wip
changes for android
```

---

## 3. Code Standards & Static Analysis

### 3.1 TypeScript & JavaScript

- TypeScript strict mode (`"strict": true`) **MUST** remain enabled across the entire repository.
- Developers **MUST NOT** use the `any` type unless explicitly justified in a code comment explaining why native interop precludes static typing.
- All code **MUST** pass ESLint checks without warnings before being proposed for merge.
- All code **MUST** be formatted using the repository's Prettier specification:
  - Indentation: 2 spaces
  - Quotes: Single (`'`)
  - Semicolons: Required (`true`)
  - Line length limit: 100 characters

### 3.2 Error Handling & Resilience

- Every bridge invocation crossing from JavaScript into the Capacitor native layer **MUST** be wrapped in defensive error-handling logic (`try/catch`).
- The application **MUST NOT** throw uncaught promise rejections on the client window.

---

## 4. Security, Secrets & Privacy Standards

- Secrets, private keys, passwords, and service tokens **MUST NEVER** be committed to version control.
- The following files **MUST** be explicitly listed in `.gitignore` and **MUST NEVER** appear in repository commits:
  - `*.keystore` and `*.jks`
  - `key.properties`
  - `google-services.json`
  - `.env`, `.env.*` (except `.env.example`)
  - `node_modules/`
  - `android/.gradle/`
  - `android/app/build/`
- Cleartext HTTP traffic **MUST NOT** be permitted in release builds. All network requests **MUST** terminate over HTTPS/TLS 1.2 or TLS 1.3.
- WebView debugging **MUST** be programmatically disabled in release APKs and AABs:
  ```java
  if (BuildConfig.DEBUG) {
      WebView.setWebContentsDebuggingEnabled(true);
  }
  ```

---

## 5. Capacitor & Native Platform Rules

### 5.1 Native Synchronization

- After any change to `capacitor.config.ts`, `package.json`, or any `@capacitor/*` plugin installation, the developer or agent **MUST** run:
  ```bash
  npx cap sync android
  ```
- Developers **MUST NOT** manually edit files inside:
  - `android/app/src/main/assets/public/`
  - `android/app/src/main/assets/capacitor.plugins.json`
  - `android/app/src/main/assets/capacitor.config.json`
  These assets are managed exclusively by the Capacitor CLI compiler.

### 5.2 Preservation of Web URL Target

- The production remote URL `https://deencommerce.com/` **MUST NOT** be modified or overwritten in production branches. Staging or local development targets **MUST** be managed via environment variables.

---

## 6. Android Permissions & Manifest Governance

- The `android/app/src/main/AndroidManifest.xml` **MUST** declare only permissions strictly essential to application functionality.
- Any pull request adding an Android system permission (`<uses-permission>`) **MUST** include:
  1. Detailed justification in the pull request description.
  2. Updates to [SPECS.md](./SPECS.md) outlining user value.
  3. Updates to [SECURITY.md](./SECURITY.md) assessing privacy implications.

Current Authorized Permissions:
```xml
<!-- Core network communication for remote store loading -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

## 7. Versioning & Release Governance

- Project versions **MUST** follow [Semantic Versioning 2.0.0](https://semver.org/) (`MAJOR.MINOR.PATCH`).
- For every production release:
  - `package.json` `version` **MUST** match the release tag.
  - `android/app/build.gradle` `versionName` **MUST** match the SemVer string.
  - `android/app/build.gradle` `versionCode` **MUST** be incremented monotonically as a strictly positive integer.
- The `versionCode` **MUST NEVER** be decreased or reused across Play Store submissions.

---

## 8. Pull Request & Code Review Acceptance

A Pull Request **SHALL NOT** be merged unless:

1. ✅ The automated CI workflow (`.github/workflows/android-build.yml`) completes with green passing status.
2. ✅ The PR description contains a completed [PULL_REQUEST_TEMPLATE.md](./.github/PULL_REQUEST_TEMPLATE.md).
3. ✅ At least one maintainer has reviewed and approved the pull request.
4. ✅ No merge conflicts exist with the target branch (`main`).
5. ✅ [CHANGELOG.md](./CHANGELOG.md) has been updated under `[Unreleased]` with relevant changes.

---

*See also: [CONTRIBUTING.md](./CONTRIBUTING.md) for step-by-step setup guides and [SECURITY.md](./SECURITY.md) for vulnerability disclosure.*
