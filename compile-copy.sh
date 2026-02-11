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

echo "  ⏳ Attente du démarrage du serveur..."

# Attendre que le message de boot apparaisse dans les logs
TIMEOUT=60  # Timeout de 60 secondes
ELAPSED=0
while ! docker logs hytale 2>&1 | tail -20 | grep -q "Hytale Server Booted"; do
    if [ $ELAPSED -ge $TIMEOUT ]; then
        echo "  ⚠️  Timeout: Le serveur prend trop de temps à démarrer"
        break
    fi
    echo "  ⏳ Toujours en attente... (${ELAPSED}s)"
    sleep 3
    ELAPSED=$((ELAPSED + 3))
done

if docker logs hytale 2>&1 | tail -20 | grep -q "Hytale Server Booted"; then
    echo "  ✓ Serveur Hytale démarré avec succès !"
fi

echo ""
echo "=============================================="
echo "  ✓ TERMINÉ - Vous pouvez vous connecter !"
echo "=============================================="