# BrocPlug

![Version](https://img.shields.io/badge/version-0.0.1-blue.svg)
![Hytale](https://img.shields.io/badge/Hytale-Plugin-green.svg)

Plugin Hytale de développement pour tester et apprendre les fonctionnalités de base de l'API serveur Hytale.

## 📋 Fonctionnalités

### Commandes disponibles

| Commande | Description | Utilisation |
|----------|-------------|-------------|
| `/hello` | Affiche un message de bienvenue | Affiche un titre événementiel à l'écran |
| `/status` | Affiche les statistiques du joueur | Montre la vie, stamina, nom du joueur et du monde |

### Détails des commandes

#### `/hello`
- **Type** : AbstractPlayerCommand
- **Permissions** : Aucune requise
- **Fonctionnalité** : Affiche un titre événementiel "Hello world!" avec sous-titre
- **Thread-safety** : Utilise EventTitleUtil pour l'affichage

#### `/status`
- **Type** : AbstractPlayerCommand
- **Permissions** : Aucune requise
- **Fonctionnalité** : Récupère et affiche les statistiques du joueur
  - Nom du joueur
  - Nom du monde
  - Points de vie (HP)
  - Stamina (en pourcentage)
- **Thread-safety** : Exécute les opérations sur le thread du monde

## 🚀 Installation

### Prérequis
- Java 17 ou supérieur
- Serveur Hytale avec support des plugins
- Gradle (inclus via wrapper)

### Compilation

```bash
# Compiler le plugin
./gradlew build

# Ou utiliser le script de compilation et copie automatique
./compile-copy.sh
```

Le JAR compilé se trouve dans : `app/build/distributions/app.jar`

### Installation sur le serveur

1. Compiler le plugin avec la commande ci-dessus
2. Copier `app/build/distributions/app.jar` dans le dossier `mods/` du serveur
3. Redémarrer le serveur Hytale

## 📖 Utilisation

Une fois le plugin installé et le serveur démarré :

```bash
# Tester la commande hello
/hello

# Afficher vos statistiques
/status
```

## 🛠️ Développement

### Structure du projet

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/hytable/plugin/
│   │   │   ├── BrocPlug.java          # Classe principale du plugin
│   │   │   ├── HelloCommand.java      # Commande /hello
│   │   │   └── StatusCommand.java     # Commande /status
│   │   └── resources/
│   │       └── manifest.json          # Métadonnées du plugin
│   └── test/
│       └── java/com/hytable/plugin/
│           └── TestPlugBuildTest.java # Tests unitaires
└── build.gradle.kts                   # Configuration Gradle
```

### Classes principales

#### `BrocPlug.java`
- **Package** : `com.hytable.plugin`
- **Extends** : `JavaPlugin`
- **Rôle** : Point d'entrée du plugin, enregistre les commandes

```java
@Override
protected void setup() {
    this.getCommandRegistry().registerCommand(
        new HelloCommand("hello", "An exemple command", false));
    this.getCommandRegistry().registerCommand(
        new StatusCommand("status", "Affiche le statut du joueur", false));
}
```

#### `HelloCommand.java`
- **Extends** : `AbstractPlayerCommand`
- **Fonctionnalité** : Affiche un message de bienvenue avec EventTitleUtil

#### `StatusCommand.java`
- **Extends** : `AbstractPlayerCommand`
- **Fonctionnalité** : Récupère et affiche les statistiques du joueur
- **Utilise** : EntityStatMap, World.execute() pour thread-safety

### API Hytale utilisées

- `com.hypixel.hytale.server.core.plugin.JavaPlugin` - Base du plugin
- `com.hypixel.hytale.server.core.command.system` - Système de commandes
- `com.hypixel.hytale.server.core.modules.entitystats` - Statistiques des entités
- `com.hypixel.hytale.component` - Système de composants (Ref, Store)
- `com.hypixel.hytale.server.core.util.EventTitleUtil` - Affichage de titres

### Compiler et tester

```bash
# Compiler
./gradlew build

# Exécuter les tests
./gradlew test

# Nettoyer et recompiler
./gradlew clean build

# Voir les rapports de tests
open app/build/reports/tests/test/index.html
```

## 📝 Exemples de code

### Envoyer un message au joueur (thread-safe)

```java
@Override
protected void execute(@Nonnull CommandContext ctx, ...) {
    // Simple message
    ctx.sendMessage(Message.raw("Votre message"));
    
    // Message avec couleurs
    ctx.sendMessage(Message.raw("§aSuccès !"));
    
    // Depuis le thread du monde
    world.execute(() -> {
        ctx.sendMessage(Message.raw("Message depuis world thread"));
    });
}
```

### Récupérer les stats d'un joueur

```java
EntityStatMap statMap = (EntityStatMap) store.getComponent(
    playerRef, 
    EntityStatMap.getComponentType()
);

if (statMap != null) {
    EntityStatValue hp = statMap.get(DefaultEntityStatTypes.getHealth());
    Float currentHP = hp.get();
    Float maxHP = hp.getMax();
}
```

## 🔧 Configuration

### manifest.json

```json
{
    "Group": "com.hytable",
    "Name": "BrocPlug",
    "Version": "0.0.1",
    "Main": "com.hytable.plugin.BrocPlug"
}
```

## 📦 Dépendances

Les dépendances sont gérées via Gradle et définies dans `gradle/libs.versions.toml`.

## 🤝 Contribution

Ce projet est un plugin de développement pour l'apprentissage. N'hésitez pas à :
- Ajouter de nouvelles commandes
- Améliorer les fonctionnalités existantes
- Corriger les bugs
- Améliorer la documentation

## 📄 Licence

Projet éducatif - À usage de développement uniquement.

## 👤 Auteur

Développé par Alex pour apprendre le développement de plugins Hytale.

---

**Note** : Ce plugin est en cours de développement et destiné à l'apprentissage de l'API Hytale.
