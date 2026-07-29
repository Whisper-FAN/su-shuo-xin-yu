---
name: Shuo Xin Yu Aesthetic
colors:
  surface: '#fcf9f3'
  surface-dim: '#dcdad4'
  surface-bright: '#fcf9f3'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f6f3ed'
  surface-container: '#f0eee8'
  surface-container-high: '#ebe8e2'
  surface-container-highest: '#e5e2dc'
  on-surface: '#1c1c18'
  on-surface-variant: '#53433d'
  inverse-surface: '#31312d'
  inverse-on-surface: '#f3f0ea'
  outline: '#86736c'
  outline-variant: '#d9c2ba'
  surface-tint: '#8e4c31'
  primary: '#8c4a2f'
  on-primary: '#ffffff'
  primary-container: '#a96245'
  on-primary-container: '#fffbff'
  inverse-primary: '#ffb599'
  secondary: '#396759'
  on-secondary: '#ffffff'
  secondary-container: '#b9ead9'
  on-secondary-container: '#3d6b5e'
  tertiary: '#7a5500'
  on-tertiary: '#ffffff'
  tertiary-container: '#996c04'
  on-tertiary-container: '#fffbff'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffdbce'
  primary-fixed-dim: '#ffb599'
  on-primary-fixed: '#370e00'
  on-primary-fixed-variant: '#71361c'
  secondary-fixed: '#bceddc'
  secondary-fixed-dim: '#a0d1c0'
  on-secondary-fixed: '#002019'
  on-secondary-fixed-variant: '#204f42'
  tertiary-fixed: '#ffdeaa'
  tertiary-fixed-dim: '#f5bd58'
  on-tertiary-fixed: '#271900'
  on-tertiary-fixed-variant: '#5f4100'
  background: '#fcf9f3'
  on-background: '#1c1c18'
  surface-variant: '#e5e2dc'
typography:
  display-hero:
    fontFamily: Noto Serif SC
    fontSize: 64px
    fontWeight: '700'
    lineHeight: '1.1'
    letterSpacing: -0.02em
  display-hero-mobile:
    fontFamily: Noto Serif SC
    fontSize: 40px
    fontWeight: '700'
    lineHeight: '1.2'
  headline-lg:
    fontFamily: Noto Serif SC
    fontSize: 32px
    fontWeight: '600'
    lineHeight: '1.3'
  headline-md:
    fontFamily: Noto Serif SC
    fontSize: 24px
    fontWeight: '600'
    lineHeight: '1.4'
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: '1.6'
    letterSpacing: 0.01em
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.6'
    letterSpacing: 0.01em
  label-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '600'
    lineHeight: '1'
    letterSpacing: 0.1em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 8px
  section-gap-desktop: 120px
  section-gap-mobile: 64px
  container-max-width: 1280px
  gutter: 24px
---

## Brand & Style

The design system is rooted in the "Modern Chinese Aesthetic," blending the thousand-year heritage of ceramic artistry with contemporary digital minimalism. It targets a sophisticated audience that values cultural depth, artisanal quality, and emotional resonance.

The visual direction follows a **Museum-Quality Minimalism**. This is achieved through:
- **Spatial Breathability:** Generous white space that mimics the *Liu Bai* (intentional void) in traditional Chinese painting.
- **Organic Precision:** A fusion of geometric layouts and soft, organic edges that evoke the feel of hand-thrown pottery.
- **Tactile Digitalism:** Utilizing glassmorphism and subtle tonal layering to create a sense of physical presence without clutter.
- **Immersive Calm:** A UI that feels like walking through a high-end gallery—quiet, intentional, and premium.

## Colors

The palette is inspired by natural pigments and ceramic glazes.

- **Ivory (#F8F5EF):** The foundation. It acts as a warm, paper-like background that is softer and more premium than pure white.
- **Clay Orange (#C97C5D):** The primary brand color. Used for calls to action and focal points, representing the raw material and human touch.
- **Dark Green (#2F5D50):** The secondary anchor. Evokes traditional Celadon glazes and pine forests, providing a grounded, sophisticated contrast.
- **Ink Black (#2D2D2D):** Used for primary text and deep structural lines, mimicking high-quality calligraphy ink.
- **Gold Accent (#D9A441):** Used sparingly for decorative flourishes, icons, or high-tier highlights.

## Typography

The typography strategy relies on the contrast between the traditional "Mincho" style serifs and modern "Grotesque" sans-serifs.

- **Headlines:** Use Noto Serif SC to convey authority, history, and elegance. Hero titles should use tighter tracking to feel like a composed artwork.
- **Body Text:** Use Inter (or Noto Sans SC for Chinese locales) with increased line height and slight letter spacing. This ensures readability while maintaining an airy, modern feel.
- **Labels:** Small labels and captions should use Inter with wide tracking (all-caps for English) to provide a structural, architectural feel to the layout.

## Layout & Spacing

This design system uses a **Fluid-Fixed Hybrid Grid**. While the layout responds to screen size, content is primarily contained within a high-max-width container to preserve the "curated" gallery look.

- **Vertical Rhythm:** Large vertical gaps (120px+) between major sections are required to maintain the minimalist "void."
- **Desktop:** A 12-column grid with 24px gutters. Use asymmetrical layouts (e.g., content spanning columns 2-10) to create dynamic interest.
- **Mobile:** A 4-column grid with 16px margins. Content should be stacked, but padding should remain generous to avoid a cramped feel.
- **Transitions:** Layout changes and section reveals should be handled with staggered "fade-and-slide" entrance animations.

## Elevation & Depth

Depth is treated with extreme subtlety to mimic light hitting a smooth ceramic surface.

- **Tonal Layering:** Use slight variations of the Ivory background to define areas, rather than heavy borders.
- **Ambient Shadows:** Shadows are large, ultra-soft, and low-opacity (2-5% opacity). They should feel like the object is floating slightly above the surface rather than casting a hard shadow.
- **Glassmorphism:** Navigation bars and modal overlays use a high `backdrop-blur` (20px+) with a semi-transparent Ivory (#F8F5EF80) fill. This maintains context while providing a premium, frosted-glass texture.

## Shapes

The shape language is defined by the **"2xl" Rounded Corner**. 

- **Primary Cards & Containers:** Use a 24px (1.5rem) radius to evoke the smoothness of finished ceramics.
- **Buttons & Chips:** Use fully rounded (pill-shaped) ends for a soft, inviting touch.
- **Imagery:** Large photos should also follow the 24px radius, unless they are full-bleed sections meant to immerse the user.

## Components

### Buttons
- **Primary:** Pill-shaped, Clay Orange background with white text. No shadow, or a very faint ambient shadow on hover.
- **Secondary:** Transparent with an Ink Black thin border and pill-shaped.
- **Interaction:** Smooth 300ms color transitions and a slight scale-up (1.02x) on hover.

### Navigation Bar
- **Sticky Blur:** Always present but unobtrusive. High backdrop-blur with minimal Ink Black navigation links.
- **Logo:** Center-aligned for a formal, symmetrical "gallery" entry feel.

### Cards
- **Product/Story Cards:** Use the 24px radius, Ivory background, and a very soft shadow. 
- **Content:** Images within cards should have a subtle zoom effect on hover to emphasize the artistic detail of the ceramic works.

### Form Elements (Questionnaire)
- **Inputs:** Underline-style or very soft-filled rectangles with 12px rounding. 
- **Focus State:** Transition the border or underline color to Clay Orange with a gentle glow.
- **Steppers:** Use small, elegant Gold dots to indicate progress through the storytelling experience.

### Storytelling Sections
- **Parallax:** Background images or decorative ceramic textures should move at a slower scroll speed than the foreground text to create a multi-dimensional "depth of field" effect.