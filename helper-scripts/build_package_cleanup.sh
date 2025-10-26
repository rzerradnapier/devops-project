#!/bin/bash
set -e  # Exit immediately on error

cd ..
# --- STEP 1: Build Docker image ---
echo "🚀 Building Docker image 'database'..."
docker build -t database ./db

# --- STEP 2: Run container ---
echo "🧱 Starting container 'countries' on port 3306..."
docker run --name countries -dp 33060:3306 database

# --- STEP 3: Run Maven build ---
echo "🧪 Running Maven clean package..."
mvn clean package

# --- STEP 4: Stop container ---
echo "🛑 Stopping container 'countries'..."
docker stop countries

# --- STEP 5: Remove container ---
echo "🧹 Removing container 'countries'..."
docker rm countries

# --- STEP 6: Remove Docker image ---
echo "🧽 Removing Docker image 'database'..."
docker image rm database

echo "✅ All steps completed successfully!"