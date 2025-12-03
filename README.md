# Bloom Mood

Bloom Mood is an emotional journaling app where each logged mood becomes a flower in your personal bouquet.
The app encourages reflection through visual storytelling rather than charts or lists.
Users can log moods for today or past days, browse their calendar, edit/delete entries, and watch their bouquet grow over time.

## Figma Design

Full design prototype:
https://www.figma.com/design/acRuf1334cucHDcBXdUsug/bloom-mood?node-id=2013-473&t=Q1plMKIp2uWMxCB4-1

## Features & Technologies Used
- Jetpack Compose UI
  - Scaffold, TopAppBar, LazyVerticalGrid, BoxWithConstraints
  - Custom layout behaviors for portrait and landscape
  - Fully custom composables:
    - Bouquet — dynamically drawn vase + flowers + stems
    - GlassButton — frosted-glass circular navigation buttons
- Custom Graphics
  - Bouquet illustration rendered from:
    - Vase PNG layers (bottle.png, base_shine.png)
    - Custom flower PNGs mapped to mood families
    - Curved stems drawn using Canvas + Bézier curves
- Architecture
  - ViewModel + StateFlow
  - Screens observe state with collectAsState()
- Persistence
  - Kotlin Serialization (kotlinx.serialization)
  - JSON storage via internal app storage (mood_entries.json)
  - Repository handles:
    - upsert (per-day replacement)
    - delete
    - range queries (like entriesInRange)
    
## Device & SDK Notes
- minSdk = 24
- target/compileSdk = 36
- Works on phones, tablets, portrait & landscape
- No special device hardware needed
- Local-only storage (uninstalling clears moods)

## Notable Enhancements

- Custom-drawn stems with Bézier curves
- Tuned bouquet layout with hand-drawn art layers
- Glass UI aesthetic buttons using blur-like visuals
- Friendly edit/delete flow in the calendar

Bloom Mood blends emotional journaling with calm visual design.
Using Jetpack Compose, dynamic graphics, and simple persistent storage, the app offers a gentle and aesthetic way to track your moods over time.
