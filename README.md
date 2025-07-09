
<p align="center">
  <img src="/app/src/main/res/mipmap-anydpi-v26/ic_launcher.png" width="160" alt="TWRP Builder Logo" />
</p>

<h1 align="center">TWRP Builder</h1>

> 🔧 A self-sustained Android APK tool for building, customizing, and backing up TWRP recovery images — powered entirely on Android using WebView, Termux, and root-friendly shell automation.

---

## 📱 What Is TWRP Builder?

**TWRP Builder** is a hybrid WebView-based Android app that lets you build your own TWRP custom recovery image from source or modify existing ones. It also lets you monitor build logs, execute ADB commands, and optionally back up vital partitions like `boot.img` and `vendor_boot.img`.

Built to function **entirely on mobile**, with **zero PC dependency**, it's an essential tool for Android modders, kernel devs, and recovery tweakers.

---

## 🧰 Features

✅ Offline Android WebView app (runs your HTML/JS UI locally)  
✅ Full-featured TWRP Build Panel (codename, Android version, custom flags)  
✅ Boot image picker (select local `boot.img` or other partition sources)  
✅ 🔐 Root-only Partition Backup tab:  
  • `boot.img`, `vendor_boot.img`, `recovery.img`, `dtbo.img`, `boot_init.img`  
✅ 🧠 Live Logcat Terminal (see build process or device logs in realtime)  
✅ 💻 ADB Shell Command Interface (with planned Termux integration)  
✅ 📦 Auto-saves logs and backups to `/sdcard/TWRP_Builder/`  
✅ 🎨 Material Dark Theme with responsive UI  
✅ ⚙️ GitHub Actions workflow support to build `.apk` with no PC

---

## 📸 Screenshots
_(To be added after first release)_

---

## 🚀 Usage

### 🧪 Start Building
1. Open the app on your device
2. Enter device codename (e.g. `whyred`)
3. Select Android version (13, 14, 15 supported)
4. Choose your boot image
5. (Optional) Enter custom build flags
6. Tap `Build TWRP` — this currently simulates the build process (real backend coming soon!)

---

### 🔐 Root Partition Backup
1. Switch to the **Partition Backup tab**
2. Check any or all:
   - `boot.img`
   - `vendor_boot.img`
   - `dtbo.img`
   - `recovery.img`
   - `boot_init.img`
3. Tap `Start Backup`
4. Files are saved to `/sdcard/TWRP_Builder/backups/`

---

### 🧠 Termux Integration

Coming soon, with intents and bridge commands to execute ADB and Git from Termux shell:

```bash
am start -a com.termux.RUN_COMMAND   --es com.termux.RUN_COMMAND_PATH "/data/data/com.termux/files/usr/bin/adb"   --es com.termux.RUN_COMMAND_ARGUMENTS "devices"   --ez com.termux.RUN_COMMAND_BACKGROUND true   com.termux
```

---

## 📦 GitHub Actions: CI Build

Add this repo to your GitHub and enable Actions. Then every push builds the APK:

📁 `.github/workflows/android-build.yml`

```yaml
on:
  push:
    branches: [ main ]
jobs:
  build:
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      - name: Setup JDK 17
        uses: actions/setup-java@v4
      - name: Build APK
        run: ./gradlew assembleDebug
```

---

## 👨‍🚀 Author

**JADIS (Justadudeinspace)**  
🔗 [GitHub](https://github.com/Justadudeinspace)  
📧 justadudeinspace@gmail.com

---

## 📃 License

```
MIT License — do what you will, give credit where due.
TWRP is trademarked by TeamWin Recovery Project.
```

---

## 💬 Contributing

PRs are welcome. Submit issues or feature requests through the Issues tab.  
If you're using Termux or contributing shell scripts, please label accordingly.

