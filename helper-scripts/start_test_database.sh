#!/bin/bash
set -e  # Exit immediately if a command fails

cd ..
echo "🚀 Building Docker image 'database' from ./db ..."
docker build -t database ./db

echo "🧱 Running container 'countries' on port 3306..."
docker run --name countries -dp 3306:3306 database

echo "✅ Database container 'countries' is up and running!"