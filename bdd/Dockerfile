# pull official base image
FROM postgres:12.2-alpine
LABEL Author="Team Monstreuils"
LABEL Project="mee-base"

#set french timezone
RUN apk update && \
    apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Europe/Paris /etc/localtime && \
    echo "Europe/Paris" >  /etc/timezone && \
    apk del tzdata

# run init.sql mis en commentaire le sql est maintenent généré via flyway
# COPY sql/* /docker-entrypoint-initdb.d/

