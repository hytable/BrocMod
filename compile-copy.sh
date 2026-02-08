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

echo "→ Extraction du manifest.json..."
NAME=$(grep -o '"Name": "[^"]*' app/src/main/resources/manifest.json | cut -d'"' -f4)
JAR_NAME="${NAME}.jar"

echo "  Nom: $NAME"
echo "  JAR final: $JAR_NAME"
echo ""

echo "→ Copie du fichier JAR..."
cp app/build/libs/"$JAR_NAME" /mnt/c/Hytale/server-files/mods/"$JAR_NAME"
echo ""
echo "----------------------------------------------"
echo ""

echo "→ Redémarrage du conteneur Docker..."
docker restart hytale
echo ""
echo "=============================================="
echo "  ✓ TERMINÉ"
echo "=============================================="