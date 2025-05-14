#!/bin/bash

DOCKER_USERNAME=eskandergharbi
VERSION=1.0.0
SERVICES=(
discovery-server
)

echo "🔐 Connexion à Docker Hub..."
docker login || { echo "❌ Échec de connexion à Docker Hub"; exit 1; }

for SERVICE in "${SERVICES[@]}"; do
  IMAGE_NAME=$DOCKER_USERNAME/$SERVICE:$VERSION

  echo ""
  echo "🔨 [$SERVICE] Building Docker image: $IMAGE_NAME"
  cd $SERVICE || { echo "❌ Dossier $SERVICE introuvable"; continue; }

  docker build -t $IMAGE_NAME . || { echo "❌ Échec du build Docker pour $SERVICE"; cd ..; continue; }

  echo "🚀 [$SERVICE] Pushing to Docker Hub..."
  docker push $IMAGE_NAME || { echo "❌ Échec du push pour $SERVICE"; cd ..; continue; }

  echo "✅ [$SERVICE] Image buildée et poussée avec succès"
  cd ..
done

echo ""
echo "🎉 Toutes les images ont été traitées."
