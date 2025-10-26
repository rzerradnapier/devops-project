#!/bin/bash
# =====================================================================
# Docker Compose Cleanup Script
# Stops, removes, and optionally rebuilds your Compose environment.
# Usage:
#   ./docker-cleanup.sh         → stop & remove containers, images, volumes
#   ./docker-cleanup.sh rebuild → clean and rebuild fresh containers
# =====================================================================

set -e  # exit immediately on error


cd ..
echo "🧹 Cleaning up Docker Compose environment"
echo "-----------------------------------------------------------"

# Stop and remove containers, networks, volumes, and images
docker compose down -v --rmi all || true

# Optional: prune dangling images, volumes, and networks
echo "🗑  Removing dangling images, volumes, and networks..."
docker system prune -af --volumes

echo "✅ Docker Compose environment cleaned successfully."

# Rebuild option
if [ "$1" == "rebuild" ]; then
  echo "🔧 Rebuilding containers from scratch..."
  docker compose build --no-cache
  docker compose up -d
  echo "🚀 Rebuild complete. Containers are running."
fi