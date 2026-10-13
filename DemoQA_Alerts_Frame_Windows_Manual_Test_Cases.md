# Manual Test Cases – DemoQA "Alerts, Frame & Windows"

| Field | Value |
|---|---|
| Application | DemoQA (https://demoqa.com) |
| Start URL | https://demoqa.com/browser-windows |
| Module | Alerts, Frame & Windows |
| Scope | Browser Windows (main), every left-side menu item of the module (Alerts, Frames, Nested Frames, Modal Dialogs), left sidebar navigation (all groups and items) |
| Test type | Functional, UI, Navigation, Negative, Cross-browser, Responsive, Accessibility |
| Browsers | Chrome, Firefox, Edge, Safari (latest) |
| Prepared on | 12-Oct-2026 |

**Priority:** P1 = Critical, P2 = High, P3 = Medium, P4 = Low

**Common precondition (all test cases):** Browser open, internet connected, pop-up blocker disabled for demoqa.com (unless the test says otherwise).

---

## Table of Contents

1. [Left Sidebar Navigation (all groups and items)](#1-left-sidebar-navigation)
2. [Browser Windows](#2-browser-windows)
3. [Alerts](#3-alerts)
4. [Frames](#4-frames)
5. [Nested Frames](#5-nested-frames)
6. [Modal Dialogs](#6-modal-dialogs)
7. [Common / Non-Functional (all pages)](#7-common--non-functional-all-pages)
8. [Summary](#8-summary)

---

## 1. Left Sidebar Navigation

**Expected sidebar structure**

| Group | Items (URL) |
|---|---|
| Elements | Text Box (`/text-box`), Check Box (`/checkbox`), Radio Button (`/radio-button`), Web Tables (`/webtables`), Buttons (`/buttons`), Links (`/links`), Broken Links - Images (`/broken`), Upload and Download (`/upload-download`), Dynamic Properties (`/dynamic-properties`) |
| Forms | Practice Form (`/automation-practice-form`) |
| Alerts, Frame & Windows | Browser Windows (`/browser-windows`), Alerts (`/alerts`), Frames (`/frames`), Nested Frames (`/nestedframes`), Modal Dialogs (`/modal-dialogs`) |
| Widgets | Accordian (`/accordian`), Auto Complete (`/auto-complete`), Date Picker (`/date-picker`), Slider (`/slider`), Progress Bar (`/progress-bar`), Tabs (`/tabs`), Tool Tips (`/tool-tips`), Menu (`/menu`), Select Menu (`/select-menu`) |
| Interactions | Sortable (`/sortable`), Selectable (`/selectable`), Resizable (`/resizable`), Droppable (`/droppable`), Dragabble (`/dragabble`) |
| Book Store Application | Login (`/login`), Register (`/register`, disabled), Book Store (`/books`), Profile (`/profile`), Book Store API (`/swagger`) |

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_NAV_001 | Sidebar is displayed on page load | 1. Open https://demoqa.com/browser-windows | Left sidebar visible with 6 groups in order: Elements, Forms, Alerts, Frame & Windows, Widgets, Interactions, Book Store Application | P1 |
| TC_NAV_002 | Current group expanded by default | 1. Open /browser-windows | "Alerts, Frame & Windows" group expanded; other groups collapsed | P2 |
| TC_NAV_003 | Active item highlighted | 1. Open /browser-windows | "Browser Windows" item shown as active/highlighted in sidebar | P2 |
| TC_NAV_004 | Items of current group listed in order | 1. Open /browser-windows<br>2. Read expanded group items | Items shown in order: Browser Windows, Alerts, Frames, Nested Frames, Modal Dialogs | P2 |
| TC_NAV_005 | Collapse current group | 1. Click group header "Alerts, Frame & Windows" | Group collapses; item list hidden; page content unchanged | P3 |
| TC_NAV_006 | Re-expand group | 1. Collapse group (TC_NAV_005)<br>2. Click header again | Group expands; all 5 items visible again | P3 |
| TC_NAV_007 | Expand "Elements" group | 1. Click "Elements" header | Group expands and shows all 9 items listed in table above | P2 |
| TC_NAV_008 | Expand "Forms" group | 1. Click "Forms" header | Shows "Practice Form" | P2 |
| TC_NAV_009 | Expand "Widgets" group | 1. Click "Widgets" header | Shows all 9 Widgets items listed above | P2 |
| TC_NAV_010 | Expand "Interactions" group | 1. Click "Interactions" header | Shows all 5 Interactions items listed above | P2 |
| TC_NAV_011 | Expand "Book Store Application" group | 1. Click "Book Store Application" header | Shows Login, Register, Book Store, Profile, Book Store API | P2 |
| TC_NAV_012 | Navigate to Alerts | 1. Click "Alerts" in sidebar | URL = /alerts; heading "Alerts"; "Alerts" item active | P1 |
| TC_NAV_013 | Navigate to Frames | 1. Click "Frames" | URL = /frames; heading "Frames"; item active | P1 |
| TC_NAV_014 | Navigate to Nested Frames | 1. Click "Nested Frames" | URL = /nestedframes; heading "Nested Frames"; item active | P1 |
| TC_NAV_015 | Navigate to Modal Dialogs | 1. Click "Modal Dialogs" | URL = /modal-dialogs; heading "Modal Dialogs"; item active | P1 |
| TC_NAV_016 | Navigate back to Browser Windows | 1. From /alerts click "Browser Windows" | URL = /browser-windows; heading "Browser Windows" | P1 |
| TC_NAV_017 | Navigate to every Elements item | 1. Click each Elements item one by one | Each opens correct URL (table above), page heading matches item name, no error/blank page | P2 |
| TC_NAV_018 | Navigate to Practice Form | 1. Click "Practice Form" | URL = /automation-practice-form; form page loads | P2 |
| TC_NAV_019 | Navigate to every Widgets item | 1. Click each Widgets item one by one | Each opens correct URL, heading matches, no error | P2 |
| TC_NAV_020 | Navigate to every Interactions item | 1. Click each Interactions item one by one | Each opens correct URL, heading matches, no error | P2 |
| TC_NAV_021 | Navigate to Book Store items | 1. Click Login, Book Store, Profile, Book Store API | Login → /login, Book Store → /books, Profile → /profile, Book Store API → /swagger | P2 |
| TC_NAV_022 | Disabled "Register" item | 1. Expand Book Store Application<br>2. Click "Register" | Item looks disabled; click does not navigate (or matches defined behaviour) | P3 |
| TC_NAV_023 | Browser Back/Forward | 1. /browser-windows → click Alerts → click Frames<br>2. Press Back twice, then Forward | Back: /alerts then /browser-windows. Forward: /alerts. Active item and content match URL each time | P2 |
| TC_NAV_024 | Deep link loads correct state | 1. Paste https://demoqa.com/modal-dialogs in new tab | Modal Dialogs page loads directly; correct group expanded; item active | P2 |
| TC_NAV_025 | Refresh keeps page | 1. On /frames press F5 | Same page reloads, sidebar state correct | P3 |
| TC_NAV_026 | Page logo / home link | 1. Click top header logo | Navigates to https://demoqa.com home page | P3 |
| TC_NAV_027 | Invalid route | 1. Open https://demoqa.com/browser-windowsxyz | Graceful handling (404/home), no crash or blank white screen | P3 |
| TC_NAV_028 | Keyboard navigation of sidebar | 1. Use Tab to move to sidebar items<br>2. Press Enter on "Alerts" | Focus visible on items; Enter opens /alerts | P3 |
| TC_NAV_029 | Hover state | 1. Hover each sidebar item | Hover style shown; cursor = pointer | P4 |

---

## 2. Browser Windows

**URL:** https://demoqa.com/browser-windows

| Element | Locator | Behaviour |
|---|---|---|
| Heading | `h1` – "Browser Windows" | – |
| New Tab | `#tabButton` | Opens `/sample` in new tab |
| New Window | `#windowButton` | Opens `/sample` in new window, 400×400 px at top 500 / left 500 |
| New Window Message | `#messageWindowButton` | Opens 500×200 window (name `MsgWindow`) with text message |
| Sample page | `#sampleHeading` | "This is a sample page" (dark grey background) |

### 2.1 UI

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_BW_001 | Page loads | 1. Open /browser-windows | Page loads without error; heading "Browser Windows" centered | P1 |
| TC_BW_002 | Buttons present | 1. Observe main area | 3 buttons visible: "New Tab", "New Window", "New Window Message" | P1 |
| TC_BW_003 | Button labels and order | 1. Read buttons top-to-bottom | Order: New Tab, New Window, New Window Message; spelling exact | P3 |
| TC_BW_004 | Buttons enabled | 1. Inspect each button | All enabled and clickable | P2 |
| TC_BW_005 | Button styling | 1. Observe buttons<br>2. Hover each | Consistent style (blue primary); hover state; pointer cursor | P4 |
| TC_BW_006 | Page title | 1. Check browser tab title | Title shown (currently "demosite"); same on all pages | P4 |

### 2.2 New Tab

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_BW_007 | New Tab opens new tab | 1. Click "New Tab" | New browser tab opens and gets focus | P1 |
| TC_BW_008 | New tab URL | 1. Click "New Tab"<br>2. Check URL of new tab | URL = https://demoqa.com/sample | P1 |
| TC_BW_009 | New tab content | 1. Click "New Tab" | Heading "This is a sample page" (`#sampleHeading`) shown on dark grey background | P1 |
| TC_BW_010 | Parent tab unchanged | 1. Click "New Tab"<br>2. Switch back to original tab | Original tab still on /browser-windows, state unchanged | P2 |
| TC_BW_011 | Close new tab | 1. Click "New Tab"<br>2. Close new tab | Focus returns to parent; parent works normally | P2 |
| TC_BW_012 | Multiple clicks | 1. Click "New Tab" 3 times (switch back each time) | 3 separate tabs open, each with sample page | P3 |
| TC_BW_013 | Tab count / window handles | 1. Note tab count<br>2. Click "New Tab" | Tab count increases by exactly 1 | P2 |
| TC_BW_014 | Refresh new tab | 1. Open new tab<br>2. Press F5 there | Sample page reloads correctly | P4 |

### 2.3 New Window

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_BW_015 | New Window opens separate window | 1. Click "New Window" | New separate browser window (not tab) opens | P1 |
| TC_BW_016 | New window URL | 1. Click "New Window"<br>2. Check URL | URL = https://demoqa.com/sample | P1 |
| TC_BW_017 | New window content | 1. Click "New Window" | "This is a sample page" visible | P1 |
| TC_BW_018 | New window size/position | 1. Click "New Window"<br>2. Observe size and position | Approx. 400×400 px, positioned near top 500 / left 500 | P3 |
| TC_BW_019 | New window resizable / scrollable | 1. Drag window edge<br>2. Scroll if possible | Window can be resized; scrollbars enabled | P4 |
| TC_BW_020 | Parent window unchanged | 1. Click "New Window"<br>2. Switch to parent | Parent still on /browser-windows and usable | P2 |
| TC_BW_021 | Close new window | 1. Open new window<br>2. Close it | Only child closes; parent stays open | P2 |
| TC_BW_022 | Multiple windows | 1. Click "New Window" twice | Two windows open (or second reuses per browser policy); no error | P3 |

### 2.4 New Window Message

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_BW_023 | Message window opens | 1. Click "New Window Message" | New small window opens | P1 |
| TC_BW_024 | Message text | 1. Click "New Window Message"<br>2. Read text | Text exactly: "Knowledge increases by sharing but not by saving. Please share this website with your friends and in your organization." | P1 |
| TC_BW_025 | Message window size | 1. Click button<br>2. Observe size | Approx. 500×200 px | P3 |
| TC_BW_026 | Message window URL | 1. Check address of message window | `about:blank` (no page URL), content written by script | P3 |
| TC_BW_027 | No heading/other elements | 1. Inspect message window | Only plain text; no heading, buttons or images | P4 |
| TC_BW_028 | Click twice reuses window | 1. Click "New Window Message"<br>2. Return to parent, click again | Same named window (`MsgWindow`) reused; text shown (may be duplicated – log observation) | P3 |
| TC_BW_029 | Close message window | 1. Close message window | Parent unaffected | P2 |

### 2.5 Negative / Edge

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_BW_030 | Pop-up blocker ON | 1. Enable browser pop-up blocker<br>2. Click each of the 3 buttons | Browser shows blocked pop-up notice; parent page does not crash | P2 |
| TC_BW_031 | JavaScript disabled | 1. Disable JS<br>2. Open /browser-windows | Page cannot render (SPA); no partial broken UI expected – note result | P4 |
| TC_BW_032 | Rapid clicks | 1. Click "New Tab" 5 times fast | Each click opens a tab; browser and page stay responsive | P3 |
| TC_BW_033 | Click all buttons in sequence | 1. Click New Tab → back → New Window → back → New Window Message | All 3 open correctly; all handles independent | P2 |
| TC_BW_034 | Close parent while child open | 1. Open new window<br>2. Close parent tab | Child window stays open and shows sample page | P4 |
| TC_BW_035 | Keyboard activation | 1. Tab to "New Tab"<br>2. Press Enter / Space | New tab opens (same as mouse click) | P3 |

---

## 3. Alerts

**URL:** https://demoqa.com/alerts

| Label | Button | Behaviour |
|---|---|---|
| Click Button to see alert | `#alertButton` | Alert: "You clicked a button" |
| On button click, alert will appear after 5 seconds | `#timerAlertButton` | Alert after 5 s: "This alert appeared after 5 seconds" |
| On button click, confirm box will appear | `#confirmButton` | Confirm: "Do you confirm action?" → `#confirmResult` |
| On button click, prompt box will appear | `#promtButton` | Prompt: "Please enter your name" → `#promptResult` |

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_AL_001 | Page loads | 1. Open /alerts | Heading "Alerts"; 4 labels and 4 "Click me" buttons | P1 |
| TC_AL_002 | Labels text | 1. Read labels | Text matches table above exactly | P3 |
| TC_AL_003 | Simple alert shown | 1. Click 1st "Click me" | JS alert with text "You clicked a button" | P1 |
| TC_AL_004 | Simple alert accept | 1. TC_AL_003<br>2. Click OK | Alert closes; page usable | P1 |
| TC_AL_005 | Timer alert delay | 1. Click 2nd "Click me"<br>2. Start stopwatch | No alert before ~5 s; after ~5 s alert "This alert appeared after 5 seconds" | P1 |
| TC_AL_006 | Timer alert accept | 1. TC_AL_005<br>2. Click OK | Alert closes | P2 |
| TC_AL_007 | Timer alert – page interaction during wait | 1. Click timer button<br>2. Within 5 s scroll / hover | Page usable during wait; alert still appears after 5 s | P3 |
| TC_AL_008 | Timer alert – navigate away before 5 s | 1. Click timer button<br>2. Within 5 s click "Frames" in sidebar | Observe: alert may still appear (timer not cleared) – log behaviour | P4 |
| TC_AL_009 | Confirm box text | 1. Click 3rd "Click me" | Confirm dialog "Do you confirm action?" with OK and Cancel | P1 |
| TC_AL_010 | Confirm OK | 1. TC_AL_009<br>2. Click OK | Result "You selected Ok" shown in green | P1 |
| TC_AL_011 | Confirm Cancel | 1. TC_AL_009<br>2. Click Cancel | Result "You selected Cancel" shown in green | P1 |
| TC_AL_012 | Confirm result updates | 1. Confirm → OK<br>2. Confirm → Cancel | Result updates from "Ok" to "Cancel" (single result line) | P2 |
| TC_AL_013 | Confirm Esc key | 1. Open confirm<br>2. Press Esc | Treated as Cancel → "You selected Cancel" | P3 |
| TC_AL_014 | Prompt box text | 1. Click 4th "Click me" | Prompt "Please enter your name" with text field, OK, Cancel | P1 |
| TC_AL_015 | Prompt valid name | 1. Open prompt<br>2. Type "Aruj"<br>3. OK | "You entered Aruj" shown in green | P1 |
| TC_AL_016 | Prompt Cancel | 1. Open prompt<br>2. Click Cancel | No "You entered" result shown (value null) | P2 |
| TC_AL_017 | Prompt empty + OK | 1. Open prompt<br>2. Leave empty, OK | No result shown or "You entered " with empty value – log behaviour | P3 |
| TC_AL_018 | Prompt special chars | 1. Enter `@#$%&*!` → OK | "You entered @#$%&*!" shown exactly | P3 |
| TC_AL_019 | Prompt HTML/script input (XSS) | 1. Enter `<script>alert(1)</script>` → OK | Text shown literally; no script executes | P2 |
| TC_AL_020 | Prompt long text | 1. Enter 500+ chars → OK | Full text displayed; layout not broken badly | P4 |
| TC_AL_021 | Prompt spaces / unicode | 1. Enter "  राम  " → OK | Text displayed correctly, unicode intact | P4 |
| TC_AL_022 | Prompt result updates | 1. Enter "A" → OK<br>2. Enter "B" → OK | Result shows "You entered B" | P3 |
| TC_AL_023 | Results reset on reload | 1. Produce confirm + prompt results<br>2. Refresh | Result lines cleared | P4 |

---

## 4. Frames

**URL:** https://demoqa.com/frames

| Element | Locator | Detail |
|---|---|---|
| Big frame | `#frame1` | 500×350 px, src `/sample.html` |
| Small frame | `#frame2` | 100×100 px, src `/sample.html` |
| Frame content | `#sampleHeading` | "This is a sample page" |

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_FR_001 | Page loads | 1. Open /frames | Heading "Frames"; description text; 2 iframes | P1 |
| TC_FR_002 | Description text | 1. Read text above frames | Text describes 2 iframes and switching between parent and frames | P4 |
| TC_FR_003 | Frame 1 content | 1. Look in big frame | "This is a sample page" on dark grey background | P1 |
| TC_FR_004 | Frame 2 content | 1. Look in small frame<br>2. Scroll inside | Same heading text, partially visible; scrollable inside 100×100 frame | P1 |
| TC_FR_005 | Frame 1 size | 1. Inspect `#frame1` | width 500px, height 350px | P3 |
| TC_FR_006 | Frame 2 size | 1. Inspect `#frame2` | width 100px, height 100px | P3 |
| TC_FR_007 | Frame source | 1. Inspect src of both frames | Both = `/sample.html`; same-origin | P3 |
| TC_FR_008 | Frame text selection | 1. Select text inside frame 1 | Text selectable, independent of parent page | P4 |
| TC_FR_009 | Frame scroll independent | 1. Scroll in frame 2<br>2. Scroll parent page | Each scrolls independently | P3 |
| TC_FR_010 | Open frame in new tab | 1. Right click frame → open frame in new tab (where supported) | /sample.html loads with heading | P4 |
| TC_FR_011 | Frame load after refresh | 1. Refresh page | Both frames reload with content | P3 |
| TC_FR_012 | Frame not blank on slow network | 1. DevTools throttle "Slow 3G"<br>2. Load page | Frames eventually load content; no broken frame | P4 |

---

## 5. Nested Frames

**URL:** https://demoqa.com/nestedframes

| Element | Locator | Detail |
|---|---|---|
| Parent frame | `#frame1` | src `/sampleiframe.html`, text "Parent frame" |
| Child frame | iframe inside parent | srcdoc, text "Child Iframe" |

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_NF_001 | Page loads | 1. Open /nestedframes | Heading "Nested Frames"; description; parent frame visible | P1 |
| TC_NF_002 | Description text | 1. Read text | "Sample Nested Iframe page. There are nested iframes in this page…" | P4 |
| TC_NF_003 | Parent frame text | 1. Look in outer frame | Text "Parent frame" visible | P1 |
| TC_NF_004 | Child frame text | 1. Look in inner frame | Text "Child Iframe" visible inside parent frame | P1 |
| TC_NF_005 | Child is inside parent | 1. Inspect DOM | Child iframe is a descendant of parent frame document, not main page | P2 |
| TC_NF_006 | Frame count | 1. Inspect DOM | Main page has 1 direct iframe (`#frame1`); parent has 1 child iframe | P3 |
| TC_NF_007 | Refresh | 1. Refresh page | Both frames reload with correct text | P3 |
| TC_NF_008 | Visual layout | 1. Observe frames | Child frame fully inside parent bounds; no overlap with sidebar | P4 |

---

## 6. Modal Dialogs

**URL:** https://demoqa.com/modal-dialogs

| Element | Locator | Detail |
|---|---|---|
| Info text | – | "Click on button to see modal" |
| Small modal button | `#showSmallModal` | Opens Small Modal |
| Large modal button | `#showLargeModal` | Opens Large Modal |
| Small modal title | `#example-modal-sizes-title-sm` | "Small Modal" |
| Small modal body | – | "This is a small modal. It has very less content" |
| Small modal close | `#closeSmallModal` | "Close" |
| Large modal title | `#example-modal-sizes-title-lg` | "Large Modal" |
| Large modal body | – | Lorem Ipsum paragraph |
| Large modal close | `#closeLargeModal` | "Close" |

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_MD_001 | Page loads | 1. Open /modal-dialogs | Heading "Modal Dialogs"; text "Click on button to see modal"; 2 buttons | P1 |
| TC_MD_002 | Open small modal | 1. Click "Small modal" | Small modal opens with dim overlay | P1 |
| TC_MD_003 | Small modal content | 1. TC_MD_002 | Title "Small Modal"; body "This is a small modal. It has very less content"; Close button; × icon | P1 |
| TC_MD_004 | Close small via Close button | 1. Open small modal<br>2. Click "Close" | Modal closes; overlay removed | P1 |
| TC_MD_005 | Close small via × | 1. Open small modal<br>2. Click × | Modal closes | P2 |
| TC_MD_006 | Close small via Esc | 1. Open small modal<br>2. Press Esc | Modal closes | P3 |
| TC_MD_007 | Close small via overlay click | 1. Open small modal<br>2. Click outside modal | Modal closes (backdrop click) | P3 |
| TC_MD_008 | Open large modal | 1. Click "Large modal" | Large modal opens, visibly wider than small | P1 |
| TC_MD_009 | Large modal content | 1. TC_MD_008 | Title "Large Modal"; Lorem Ipsum body ("Lorem Ipsum is simply dummy text…"); Close; × | P1 |
| TC_MD_010 | Close large via Close | 1. Open large<br>2. Click "Close" | Modal closes | P1 |
| TC_MD_011 | Close large via × / Esc / overlay | 1. Open large, close with ×<br>2. Repeat with Esc<br>3. Repeat with overlay click | Modal closes each time | P2 |
| TC_MD_012 | Background blocked | 1. Open any modal<br>2. Try clicking sidebar item | Background not clickable while modal open (click closes modal only) | P2 |
| TC_MD_013 | Reopen | 1. Open → close → open small modal | Opens again with same content | P3 |
| TC_MD_014 | Only one modal at a time | 1. Open small modal<br>2. Try to click "Large modal" | Large modal not opened over small one | P3 |
| TC_MD_015 | Focus trap | 1. Open modal<br>2. Press Tab repeatedly | Focus cycles within modal (×, Close) | P3 |
| TC_MD_016 | Page scroll locked | 1. Open large modal<br>2. Scroll | Background page does not scroll; modal content scrolls if long | P4 |
| TC_MD_017 | Modal on small screen | 1. Resize to 375px width<br>2. Open large modal | Modal fits viewport; text readable; Close reachable | P3 |

---

## 7. Common / Non-Functional (all pages)

Apply to: /browser-windows, /alerts, /frames, /nestedframes, /modal-dialogs

| TC ID | Title | Steps | Expected Result | Priority |
|---|---|---|---|---|
| TC_CM_001 | Header present | 1. Open each page | Top header with DemoQA logo shown | P3 |
| TC_CM_002 | Footer present | 1. Scroll to bottom | Footer with copyright text shown | P4 |
| TC_CM_003 | Page load time | 1. Open each page, measure with DevTools | Loads within ~3 s on normal network | P3 |
| TC_CM_004 | No console errors | 1. Open DevTools Console<br>2. Load each page, use all buttons | No uncaught JS errors from the app | P3 |
| TC_CM_005 | Cross-browser | 1. Run P1 cases in Chrome, Firefox, Edge, Safari | Same behaviour on all browsers | P2 |
| TC_CM_006 | Responsive – tablet | 1. Viewport 768px | Sidebar and content readable; no overlap | P3 |
| TC_CM_007 | Responsive – mobile | 1. Viewport 375px | Sidebar collapses/stacks; buttons usable; no horizontal scroll | P3 |
| TC_CM_008 | Zoom 200% | 1. Browser zoom 200% | Content readable; buttons usable | P4 |
| TC_CM_009 | Keyboard only | 1. Use only Tab/Shift+Tab/Enter on each page | All buttons and sidebar items reachable and usable; focus visible | P3 |
| TC_CM_010 | Screen reader labels | 1. Use VoiceOver/NVDA on buttons | Buttons announced with their visible text | P4 |
| TC_CM_011 | Ads do not block content | 1. Load page with ads | Ads do not cover buttons/sidebar; buttons clickable | P3 |
| TC_CM_012 | HTTPS | 1. Check address bar | Site served over HTTPS; no mixed-content warnings | P3 |

---

## 8. Summary

| Section | Test Cases | P1 |
|---|---|---|
| Left Sidebar Navigation | 29 | 6 |
| Browser Windows | 35 | 10 |
| Alerts | 23 | 9 |
| Frames | 12 | 3 |
| Nested Frames | 8 | 3 |
| Modal Dialogs | 17 | 7 |
| Common / Non-Functional | 12 | 0 |
| **Total** | **136** | **38** |

**Execution log template**

| TC ID | Browser | Executed By | Date | Actual Result | Status (Pass/Fail/Blocked) | Bug ID |
|---|---|---|---|---|---|---|
| | | | | | | |
