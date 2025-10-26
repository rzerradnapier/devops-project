#!/bin/bash
set -e  # Exit immediately if a command fails

cd ..
echo "🛑 Stopping container 'countries'..."
docker stop countries

echo "🧹 Removing container 'countries'..."
docker rm countries

echo "🧽 Removing Docker image 'database'..."
docker image rm database

echo "✅ Cleanup completed successfully!"