# mee-candidat

## Mode developpement

Lancer `ng serve`. Ouvrez votre navigateur sur  `http://localhost:4200/`.

## Mode Server-side rendering (SSR)

Lancer la commande

```
npm run build:ssr && npm run serve:ssr
```

Ouvrez votre navigateur sur http://localhost:4000

## Build

Lancer `ng build` pour build le project. Les les artefacts de construction générés dans le dossier `dist/`.
Utilser `--prod` pour le mode production.

## Lancement des tests

Lancer `ng test` pour lancer les tests unitaire

## Architecture

Dans le répertoire /home/docker/ on a le fichier docker-compose.yml pour le docker-compose
et le fichier variables.env pour les variables d'environnement  

Ce conteneur est connecté au conteneur proxy ngnix qui est dans le répertoire /home/docker/reverse-proxy

## les logs

Pour avoir les 1000 dernières ligne de logs : `docker logs -n 1000 mee-candidat`

## déploiement

Toutes les pipelines demandent des actions manuelles pour :
- builder 
- déployer



### recette

A chaque commit une pipeline est créé, une action manuelle est requise pour déployer sur le serveur de recette

### preprod

La preprod est l'image de la prod.

Pour déployer il faut :
- créer une branche depuis la brache de prod 'master' et la nommer en la préfixant avec 'feature/xxxxxxxx'
- faire le fix et commiter
- la pipeline sera alors construite et permettera de déployer en preprod

#### republier une version

1. identifier la version à re-publier
2. dans CI/CD > pipelines > bouton  "Run pipelines"
3. ajoutez la variable "BUILD_VERSION" et valoriser avec le numero de version, ex. : 1.0.34
4. ajoutez la variable "GIT_SHA" et valoriser avec le numero de commit raccourci précédé d'un tiret (uniquement pour la preprod), ex : -9594dd5c
5. publiez

### prod

Pour publier sur la prod il faut merger une branche 'feature/xxx' précédemment déployer sur la preprod sur la branche de prod 'master' et faire les actions manuelles sur la pipeline de prod alors créée.
