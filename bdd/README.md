# mee-base

## connection à la BDD avec pgadmin


Prérequis: avoir un client ssh et docker installé sur la machine locale

1. créer un dossier pgadmin avec dedans le `docker-compose.yml` suivant :

```
version: '3.3'
services:
  pgadmin:
    image: dpage/pgadmin4
    restart: unless-stopped
    env_file:
      - .pgadmin_env
    environment:
      - TZ=Europe/Paris
    extra_hosts:
      - "host.docker.internal:host-gateway"
    network_mode: "host"
```

2. ajouter le fichier `.pgadmin_env` pour le  login mot de passe contenant : 

PGADMIN_DEFAULT_EMAIL=email@quelquechose.fr
PGADMIN_DEFAULT_PASSWORD=lemotdepasse


3. lancer le tunnel ssh :

ssh -L 5432:127.0.0.1:5432 -N {votre login}@{IP MACHINE BDD}

4. lancer pgadmin sur http://127.0.0.1, loguez vous 

5. configurer la connection avec comme host : `127.0.0.1` 


