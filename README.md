# BrocPlug

![Version](https://img.shields.io/badge/version-0.0.1-blue.svg)
![Hytale](https://img.shields.io/badge/Hytale-Plugin-green.svg)

Un plugin simple pour serveur Hytale qui ajoute des commandes utiles pour les joueurs.

## ✨ Qu'est-ce que c'est ?

BrocPlug est un plugin pour serveurs Hytale permettant d'ajouter de nouvelles commandes dans le jeu. Facile à installer et à utiliser !

## 📋 Commandes disponibles

| Commande | Description |
|----------|-------------|
| `/hello` | Affiche un message de bienvenue à l'écran |
| `/status` | Affiche vos statistiques de joueur (vie, stamina, monde) |

### 💬 `/hello`
Affiche un message de bienvenue stylé directement sur votre écran. Parfait pour tester le plugin !

### 📊 `/status`
Montre vos informations actuelles :
- 👤 Votre pseudo
- 🌍 Le monde dans lequel vous êtes
- ❤️ Vos points de vie
- ⚡ Votre niveau de stamina

## 🚀 Installation

### Ce dont vous avez besoin
- Un serveur Hytale qui supporte les plugins
- Java 17 ou plus récent

### Installer le plugin

**Option 1 : Installation rapide**
1. Téléchargez le fichier `app.jar`
2. Placez-le dans le dossier `mods/` de votre serveur
3. Redémarrez le serveur

**Option 2 : Compiler vous-même**
```bash
./compile-copy.sh
```
Le plugin sera automatiquement compilé et copié au bon endroit.

## 📖 Comment utiliser

Une fois le serveur démarré avec le plugin installé, tapez simplement les commandes en jeu :

```
/hello
/status
```

C'est tout ! Aucune permission spéciale requise.

## 🛠️ Pour les développeurs

Vous voulez modifier ou améliorer le plugin ? Voici les fichiers importants :

```
app/src/main/java/com/hytable/plugin/
├── BrocPlug.java          # Fichier principal qui charge les commandes
├── HelloCommand.java      # Code de la commande /hello
└── StatusCommand.java     # Code de la commande /status
```

### Compiler le projet

```bash
# Compiler
./gradlew build

# Nettoyer et recompiler
./gradlew clean build

# Lancer les tests
./gradlew test
```

## 🤝 Contribution

N'hésitez pas à :
- ✨ Proposer de nouvelles commandes
- 🐛 Signaler des bugs
- 📝 Améliorer la documentation
- 🚀 Ajouter des fonctionnalités

## 👤 Auteur

Développé par Alex

---

**Note** : Plugin en développement actif 🚧
