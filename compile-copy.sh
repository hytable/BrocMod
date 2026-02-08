#!/bin/bash

echo "=============================================="
echo "  COMPILATION HYTALE"
echo "=============================================="
echo ""

echo "→ Navigation vers /home/alex/Hytale/"
cd /home/alex/Hytale/
echo ""

echo "→ Build Gradle..."
./gradlew build
echo ""
echo "----------------------------------------------"
echo ""

echo "→ Copie du fichier JAR..."
cp app/build/libs/app.jar /mnt/c/Hytale/server-files/mods/test-plug.jar
echo ""
echo "----------------------------------------------"
echo ""

echo "→ Redémarrage du conteneur Docker..."
docker restart hytale
echo ""
echo "=============================================="
echo "  ✓ TERMINÉ"
echo "=============================================="