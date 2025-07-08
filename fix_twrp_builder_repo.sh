#!/bin/bash

# Set working directory to twrp-builder if not already
cd ~/twrp-builder || { echo "⚠️ twrp-builder folder not found in ~/"; exit 1; }

echo "🧼 Cleaning Gradle junk and build cache..."

# Delete auto-generated folders
rm -rf .gradle/ app/.gradle/ app/build/ build/ .idea/ .cxx/ captures/ intermediates/ outputs/
rm -rf */build/ */.gradle/ */.idea/ */captures/ */outputs/

# Delete junk files across repo
find . -name "*.iml" -type f -delete
find . -name "*.lock" -type f -delete
find . -name "*.log" -type f -delete
find . -name "*.json" -type f -delete
find . -name "*.apk" -type f -delete
find . -name "*.rej" -type f -delete
find . -name "*.orig" -type f -delete

echo "✅ Cleaned up auto-generated files."

# Optional: fix Manifest or hard-coded image paths if needed
MANIFEST="app/src/main/AndroidManifest.xml"
if grep -q 'android:icon="@mipmap/T' "$MANIFEST"; then
  echo "🔧 Fixing deprecated icon reference..."
  sed -i 's|android:icon="@mipmap/T"|android:icon="@mipmap/ic_launcher"|g' "$MANIFEST"
fi

# Ensure splash asset is in the correct place
mkdir -p app/src/main/assets
if [ -f "assets/splash.png" ]; then
  mv -f assets/splash.png app/src/main/assets/splash.png
  echo "✅ splash.png moved to app/src/main/assets/"
fi

echo "📁 Current project structure clean and ready."

echo "⚠️ Gradle build disabled in this script (no AAPT2 in Termux)."
echo "💡 To build: use GitHub CI or a proper Linux env outside Termux."


