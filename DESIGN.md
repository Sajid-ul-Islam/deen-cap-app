*Visual design system, brand tokens, component patterns, and accessibility specifications for the DEEN Commerce mobile interface.*

# DESIGN.md — Mobile UI/UX & Visual Design System

---

## 📑 Table of Contents

- [1. Brand Identity & Design Philosophy](#1-brand-identity--design-philosophy)
- [2. Color System & Design Tokens](#2-color-system--design-tokens)
- [3. Typography Hierarchy](#3-typography-hierarchy)
- [4. Spacing, Sizing & Grid Architecture](#4-spacing-sizing--grid-architecture)
- [5. Component Patterns & UI Library](#5-component-patterns--ui-library)
- [6. Iconography Standards](#6-iconography-standards)
- [7. Dark Mode Architecture](#7-dark-mode-architecture)
- [8. Accessibility & Ergonomics](#8-accessibility--ergonomics)
- [9. Motion, Animations & Timing Curves](#9-motion-animations--timing-curves)
- [10. App Icon & Splash Screen Specifications](#10-app-icon--splash-screen-specifications)
- [11. Feedback States: Loading, Empty & Error](#11-feedback-states-loading-empty--error)

---

## 1. Brand Identity & Design Philosophy

**DEEN Commerce** embodies the principles of modern, premium, and ethical Islamic lifestyle commerce. The visual identity projects elegance, tranquility, trust, and modesty.

### Design Principles
- **Modest Elegance:** Restrained visual density with generous breathing room, refined accents, and high-quality photography.
- **Clarity & Purpose:** Distraction-free product discovery, explicit pricing, and clean navigational paths.
- **Native Android Harmony:** Respects Material Design 3 guidelines while staying true to the DEEN brand aesthetic.

---

## 2. Color System & Design Tokens

The color palette pairs deep Islamic forest greens with warm metallic gold accents, balanced against clean neutrals.

### 2.1 Primary Brand Palette

| Token Name | Hex Code | RGB | Role / Usage |
| :--- | :--- | :--- | :--- |
| `color-brand-primary` | `#0A4D3C` | `rgb(10, 77, 60)` | Primary brand color, headers, key buttons |
| `color-brand-primary-dark` | `#063328` | `rgb(6, 51, 40)` | Android status bar, pressed button state |
| `color-brand-primary-light` | `#117058` | `rgb(17, 112, 88)` | Hover states, active borders, chips |
| `color-brand-accent` | `#D4AF37` | `rgb(212, 175, 55)` | Metallic gold, badges, ratings, highlights |
| `color-brand-accent-hover` | `#C59B27` | `rgb(197, 155, 39)` | Gold button interaction state |

### 2.2 Neutrals & Surfaces

| Token Name | Light Theme | Dark Theme | Role |
| :--- | :--- | :--- | :--- |
| `color-surface-bg` | `#FBF9F5` (Warm Cream) | `#121615` (Deep Slate) | Window background |
| `color-surface-card` | `#FFFFFF` (Pure White) | `#1A201E` (Dark Olive Surface) | Product cards, dialogs, sheets |
| `color-text-primary` | `#1A202C` (Charcoal) | `#F7FAFC` (Off-White) | High-contrast body & headlines |
| `color-text-secondary` | `#4A5568` (Slate Grey) | `#A0AEC0` (Cool Grey) | Secondary descriptions, timestamps |
| `color-text-muted` | `#718096` | `#718096` | Placeholder text, disabled labels |
| `color-border-subtle` | `#E2E8F0` | `#2D3748` | Card dividers, input borders |

### 2.3 Semantic Feedback Colors

| State | Hex Code | Purpose |
| :--- | :--- | :--- |
| **Success** | `#2E7D32` | Order confirmation, item added to cart, stock available |
| **Warning** | `#ED6C02` | Low stock alert, payment pending, address verification |
| **Error / Destructive** | `#D32F2F` | Form validation failures, payment declined, connection dropped |
| **Information** | `#0288D1` | Delivery estimates, shipping policy notifications |

---

## 3. Typography Hierarchy

The type system prioritizes legibility, clean geometry, and multi-language script support (Arabic & Latin).

- **Primary Font Family:** `Inter`, `Poppins`, or system default `Roboto`, `sans-serif`.
- **Arabic / Script Fallback:** `Noto Sans Arabic`, `sans-serif`.

| Style Level | Size (sp) | Line Height (dp) | Weight | Letter Spacing |
| :--- | :--- | :--- | :--- | :--- |
| **Display H1** | `28sp` | `36dp` | Bold (700) | `-0.5px` |
| **Headline H2** | `22sp` | `28dp` | Bold (700) | `-0.2px` |
| **Headline H3** | `18sp` | `24dp` | SemiBold (600) | `0px` |
| **Title Medium** | `16sp` | `22dp` | Medium (500) | `0.1px` |
| **Body Regular** | `15sp` | `22dp` | Regular (400) | `0px` |
| **Body Small** | `13sp` | `18dp` | Regular (400) | `0.2px` |
| **Caption / Badge** | `11sp` | `14dp` | Medium (500) | `0.4px` |

---

## 4. Spacing, Sizing & Grid Architecture

Layouts follow an **8-point base grid** with a **4-point micro-grid** for tight spacing.

```text
4dp  -> Micro: Badges, tight icon padding
8dp  -> Small: Element gaps, button internal vertical padding
12dp -> Medium-Small: Input field vertical padding
16dp -> Standard: Screen horizontal margins, card padding
24dp -> Large: Section dividers, modal padding
32dp -> X-Large: Hero component spacing
48dp -> Minimum touch target dimension (Accessibility standard)
```

---

## 5. Component Patterns & UI Library

### 5.1 Buttons
- **Primary Action (Call to Action):**
  - Background: `color-brand-primary` (`#0A4D3C`), Text: `#FFFFFF`
  - Border radius: `8dp`
  - Minimum height: `48dp`
  - Elevation: `2dp` default, `4dp` on press
- **Secondary / Accent Action:**
  - Background: `color-brand-accent` (`#D4AF37`), Text: `#063328`
  - Used for "Buy Now" or high-priority promotions
- **Outlined / Ghost Button:**
  - Background: Transparent, Border: `1.5dp` solid `#0A4D3C`
  - Text: `#0A4D3C`

### 5.2 Product Cards
- **Dimensions:** Aspect ratio 1:1 or 4:5 for product imagery.
- **Card Background:** Surface card white (`#FFFFFF`) with subtle border `#E2E8F0` or elevation `1dp`.
- **Corner Radius:** `12dp` rounded corners.
- **Information Hierarchy:**
  1. Product Image with quick-wishlist heart icon (top right).
  2. Category tag (small uppercase gold badge).
  3. Product Title (Title Medium, max 2 lines truncated).
  4. Price display (Bold H3) + discounted original price (strikethrough).
  5. Full-width "Add to Cart" or quick shopping bag button.

### 5.3 Top App Bar
- **Height:** `56dp` (standard Android Action Bar).
- **Background:** `#0A4D3C` or clean `#FFFFFF` with brand logo centered.
- **Actions:** Left navigation/drawer icon, right search trigger and cart icon with count badge.

### 5.4 Bottom Sheet Dialogs
- **Usage:** Filtering catalog results, choosing sizes/colors, viewing delivery details.
- **Top Corners:** Rounded `16dp`.
- **Drag Handle:** Centered `32dp x 4dp` capsule in `#CBD5E0`.

---

## 6. Iconography Standards

- **Icon Set:** Google Material Symbols (Rounded or Outlined).
- **Standard Size:** `24dp x 24dp` bounding box.
- **Small Size:** `18dp x 18dp` (inline badges, ratings).
- **Stroke Width:** `2.0dp` consistent weight across all states.

---

## 7. Dark Mode Architecture

The mobile app respects Android system-wide dark mode settings:
- **Surface Elevation:** Uses tone-based elevation rather than pure drop-shadows.
- **Contrast Maintenance:** Text contrast ratios automatically adjusted to prevent eye strain while meeting WCAG 2.1 AA standards.
- **Color Mapping:**
  - Light `#0A4D3C` -> Deep Olive Slate `#0D634E`
  - Light `#FBF9F5` -> Dark Charcoal `#121615`

---

## 8. Accessibility & Ergonomics

1. **Touch Targets:** All clickable UI elements (buttons, links, form toggles) MUST have a bounding box of at least **`48dp x 48dp`**.
2. **Color Contrast:**
   - Body text against background: Minimum **`4.5:1`**.
   - Large headings and brand graphics: Minimum **`3.0:1`**.
3. **Screen Readers:** All images and iconography must provide `contentDescription` (Android) or `alt`/`aria-label` (WebView).

---

## 9. Motion, Animations & Timing Curves

- **Micro-Interactions (Button press, checkbox toggle):**
  - Duration: `150ms`
  - Curve: Standard `cubic-bezier(0.2, 0.0, 0, 1.0)`
- **Sheet Expansion & Modal Dialogs:**
  - Duration: `250ms`
  - Curve: Decelerate `cubic-bezier(0.0, 0.0, 0.2, 1.0)`
- **Page Transitions:**
  - Duration: `200ms` fade / slide horizontal

---

## 10. App Icon & Splash Screen Specifications

### 10.1 Android Adaptive Icon
- **Foreground Asset (`res/mipmap-anydpi-v26/ic_launcher.xml`):**
  - Size: `512 x 512 px` (safe zone: centered `66dp` circular diameter).
  - Graphic: Metallic Gold DEEN emblem / calligraphy on transparent canvas.
- **Background Layer:**
  - Solid color: `#0A4D3C` (Islamic Forest Green).

### 10.2 Splash Screen
- **Color:** `#0A4D3C`.
- **Center Graphic:** Vector logo (`deen_logo_vector.xml`) centered vertically and horizontally.
- **Fade Duration:** `200ms` smooth fade-out once WebView paints content.

---

## 11. Feedback States: Loading, Empty & Error

### 11.1 Loading Skeletons
- Light shimmering pulse animation (`#E2E8F0` to `#EDF2F7`).
- Product cards render skeleton image box and three text placeholder bars while remote data streams.

### 11.2 Empty States
- Custom illustration (e.g., empty shopping tote in brand green outline).
- Clear heading: *"Your Cart is Empty"*.
- Actionable button: *"Explore Best Sellers"*.

### 11.3 Offline / Error State
- Screen: Local native shell (`www/index.html`).
- Graphic: Disconnected cloud / signal icon.
- Message: *"No Internet Connection. Please verify your mobile data or Wi-Fi."*
- Primary CTA: *"Try Again"* with spinning retry indicator.

---

*See also: [SPECS.md](./SPECS.md) for functional requirements and [ARCHITECTURE.md](./ARCHITECTURE.md) for technical structure.*
