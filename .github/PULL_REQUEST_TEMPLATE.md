*Standard pull request submission template for DEEN Commerce contributions.*

## 📝 Description

Please provide a clear and concise summary of the changes introduced in this pull request, along with the motivation and architectural context.

---

## 🏷️ Type of Change

Please select all that apply:

- [ ] 🐛 **Bug fix** (non-breaking change fixing an issue)
- [ ] ✨ **New feature** (non-breaking change adding functionality or native plugins)
- [ ] 💥 **Breaking change** (fix or feature causing existing functionality to break)
- [ ] 📚 **Documentation** (updates to docs, README, or architecture guides)
- [ ] 🔧 **Chore / Tooling** (dependency bumps, CI updates, Gradle configuration)
- [ ] ⚡ **Performance improvement** (optimizations to WebView or startup time)

---

## 🔗 Related Issues

Closes # (issue number)  
Relates to # (issue number)

---

## 📸 Screenshots / Recordings (if applicable)

*If this PR modifies UI, splash screens, or mobile interaction flows, please attach before/after screenshots or screen recordings.*

| Before | After |
| :---: | :---: |
| *(Image / GIF)* | *(Image / GIF)* |

---

## 🧪 Test Plan & Verification

Please describe the tests executed to verify your changes:

- **Target Device(s) Tested:** (e.g., Google Pixel 7 Android 14, Samsung Galaxy S22 Android 13, Emulator API 34)
- **Commands Executed:**
  - [ ] `npm run lint`
  - [ ] `npx tsc --noEmit`
  - [ ] `npx cap sync android`
  - [ ] `cd android && ./gradlew assembleDebug`
- **Manual Verification Steps:**
  1. 
  2. 
  3. 

---

## ✅ Pre-Merge Checklist

Before submitting, please ensure you have satisfied all items:

- [ ] My code adheres to the engineering standards specified in [RULES.md](../RULES.md).
- [ ] I have verified that no secrets, keystores, or `.env` files are included in this PR.
- [ ] I have executed `npx cap sync android` if `capacitor.config.ts` or plugins were touched.
- [ ] I have updated the relevant documentation (e.g., `README.md`, `ARCHITECTURE.md`).
- [ ] I have updated `[Unreleased]` in [CHANGELOG.md](../CHANGELOG.md).
- [ ] Automated CI checks pass cleanly.
