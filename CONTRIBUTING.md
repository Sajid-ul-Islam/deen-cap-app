*Contribution guidelines, development environment setup, branching workflows, and code review standards.*

# CONTRIBUTING.md — Contributor Guide & Engineering Workflows

---

## 📑 Table of Contents

- [1. Welcome & Engineering Culture](#1-welcome--engineering-culture)
- [2. Prerequisites & Environment Setup](#2-prerequisites--environment-setup)
- [3. Local Development Step-by-Step](#3-local-development-step-by-step)
- [4. Git Workflow & Branching Conventions](#4-git-workflow--branching-conventions)
- [5. Commit Message Standard](#5-commit-message-standard)
- [6. Pull Request Lifecycle](#6-pull-request-lifecycle)
- [7. Code Review Checklist](#7-code-review-checklist)
- [8. Issue Reporting & Bug Submissions](#8-issue-reporting--bug-submissions)
- [9. Contact & Community Support](#9-contact--community-support)

---

## 1. Welcome & Engineering Culture

Welcome to the **DEEN Commerce** contributor community! We are excited to collaborate with engineers, designers, and testers building a premier mobile shopping experience.

All contributors are expected to uphold the standards described in our [CODE_OF_CONDUCT.md](./CODE_OF_CONDUCT.md) and adhere to the strict engineering guidelines detailed in [RULES.md](./RULES.md).

---

## 2. Prerequisites & Environment Setup

Ensure your local development workstation satisfies the following requirements:

| Tool | Version Requirement | Installation / Verification Command |
| :--- | :--- | :--- |
| **Node.js** | `>= 18.18.0` (LTS) | `node -v` |
| **npm** | `>= 9.0.0` | `npm -v` |
| **Java JDK** | `17` (Eclipse Temurin) | `java -version` |
| **Android Studio** | `Hedgehog (2023.1.1)+` | Verify Android SDK Platform 34 installed |
| **Git** | `>= 2.38.0` | `git --version` |

### Environment Variables
Configure your shell profile (`~/.bashrc`, `~/.zshrc`) with the Android SDK location:

```bash
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
```

---

## 3. Local Development Step-by-Step

### Step 1: Fork and Clone the Repository

```bash
git clone https://github.com/deencommerce/deen-cap.git
cd deen-cap
```

### Step 2: Install Dependencies

```bash
npm install
```

### Step 3: Synchronize Capacitor Native Android Project

```bash
npx cap sync android
```

### Step 4: Launch via Android Studio or Terminal

To launch the project in Android Studio:
```bash
npx cap open android
```

To build and deploy directly to a connected USB Android device (with USB debugging enabled):
```bash
npx cap run android
```

---

## 4. Git Workflow & Branching Conventions

We use **GitHub Flow** with descriptive branch names. Never push directly to `main`.

### Branch Naming Taxonomy
```text
feat/<feature-summary>      # e.g., feat/biometric-auth
fix/<defect-summary>        # e.g., fix/cookie-persistence
chore/<tooling-summary>     # e.g., chore/upgrade-capacitor-6
docs/<documentation-scope>  # e.g., docs/add-architecture-diagrams
refactor/<component-name>   # e.g., refactor/back-button-handler
test/<test-scope>           # e.g., test/smoke-test-checklist
```

---

## 5. Commit Message Standard

We enforce [Conventional Commits 1.0.0](https://www.conventionalcommits.org/).

### Format
```text
<type>(<optional scope>): <short description in imperative mood>

[optional longer body detailing rationale]

[optional issue reference: Fixes #123]
```

### Examples
- `feat(push): add Firebase Cloud Messaging registration listener`
- `fix(backbutton): prevent immediate app exit when history stack exists`
- `docs(deployment): document Play Console internal track rollout`

---

## 6. Pull Request Lifecycle

1. **Keep PRs Focused:** Limit pull requests to a single feature or bug fix.
2. **Sync Native Assets:** Always verify `npx cap sync android` produces clean git diffs.
3. **Fill the Template:** Fill out all sections of [.github/PULL_REQUEST_TEMPLATE.md](./.github/PULL_REQUEST_TEMPLATE.md).
4. **CI Passing:** The GitHub Actions pipeline (`.github/workflows/android-build.yml`) must pass cleanly.
5. **Squash and Merge:** PRs are squash-merged into `main` to preserve a clean, linear git history.

---

## 7. Code Review Checklist

Before requesting review, verify:

- [ ] Code strictly follows TypeScript standards (no untyped `any`).
- [ ] No credentials, keystores, or `.env` files are tracked in git.
- [ ] `./gradlew assembleDebug` compiles without errors.
- [ ] Hardware back button navigation was tested on a physical device or emulator.
- [ ] Documentation and [CHANGELOG.md](./CHANGELOG.md) have been updated.

---

## 8. Issue Reporting & Bug Submissions

- **Bug Reports:** Use [.github/ISSUE_TEMPLATE/bug_report.md](./.github/ISSUE_TEMPLATE/bug_report.md). Include device model, Android OS version, and reproducible steps.
- **Feature Requests:** Use [.github/ISSUE_TEMPLATE/feature_request.md](./.github/ISSUE_TEMPLATE/feature_request.md). Explain the user problem and proposed solution.

---

## 9. Contact & Community Support

- **Engineering Lead:** `[YOUR_EMAIL]`
- **Community Chat:** Join our Discord/Slack at `[YOUR_COMMUNITY_LINK]` (TODO: add invite link)
- **Security Inquiries:** For private security reports, refer directly to [SECURITY.md](./SECURITY.md).

Thank you for contributing to DEEN Commerce!
