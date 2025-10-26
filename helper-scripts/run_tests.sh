#!/bin/bash
set -e  # Exit immediately if a command exits with a non-zero status

echo "🚀 Building Docker image 'database'..."
cd ..
docker build -t database ./db

echo "🧱 Starting container 'countries' on port 3306..."
docker run --name countries -dp 33060:3306 database

echo "🧪 Running Maven tests..."
mvn clean test

echo "🛑 Stopping container 'countries'..."
docker stop countries

echo "🧹 Removing container 'countries'..."
docker rm countries

echo "🧽 Removing Docker image 'database'..."
docker image rm database

echo "✅ All steps completed successfully!"