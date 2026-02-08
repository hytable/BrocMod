# TestPlug

Plugin Hytale pour tester les fonctionnalités de base.

## Fonctionnalités

- **HelloCommand** : Affiche un message de bienvenue aux joueurs

## Installation

1. Compiler : `./gradlew build`
2. Déplacer `app/build/distributions/app.jar` vers `mods/`
3. Redémarrer le serveur

## Utilisation

```
/hello
```

Affiche un titre "Hello world!" au joueur.

## Développement

- Plugin principal : `com.hytable.plugin.TestPlug`
- Commande : `com.hytable.plugin.HelloCommand`
