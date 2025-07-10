
#!/bin/bash

# Path to README.md in main directory
README_SRC="README.md"
README_DEST="./.github/workflows/README.md"

# Make sure GitHub workflows folder exists
mkdir -p .github/workflows

# Copy README into GitHub workflows folder (or wherever we want to include it pre-push)
cp "$README_SRC" "$README_DEST"

echo "README.md copied to workflows directory. Ready for commit."
