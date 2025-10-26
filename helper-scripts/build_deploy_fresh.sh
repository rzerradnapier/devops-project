#!/bin/bash
# =====================================================================
# start_fresh.sh
# Runs build_package_cleanup.sh, then docker-cleanup.sh, then
# starts the application with docker compose.
# =====================================================================

set -e  # Exit immediately on error

# --- Step 1: Build, package, and cleanup ---
echo "🚀 Running build_package_cleanup.sh..."
chmod +x ./build_package_cleanup.sh

# --- Step 2: Clean up Docker environment ---
echo "🧹 Running docker-cleanup.sh..."
chmod +x ./docker_cleanup.sh

# --- Step 3: Start application ---
echo "🏗️  Starting application with docker compose..."
cd ..
docker compose up -d --build

echo "✅ Application started successfully!"