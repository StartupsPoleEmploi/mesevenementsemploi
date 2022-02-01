#BUILD
FROM node:12.6.0-alpine as builder
WORKDIR app
COPY package.json .
RUN npm install
ADD . .
RUN npm run build-prod


FROM nginx:1.17-alpine
LABEL Author="Team Monstreuils"
LABEL Project="mee-conseiller"

RUN echo 'http://dl-cdn.alpinelinux.org/alpine/v3.11/main/' > /etc/apk/repositories && \
    echo 'http://dl-cdn.alpinelinux.org/alpine/v3.11/community/' >> /etc/apk/repositories && \
    echo 'http://dl-cdn.alpinelinux.org/alpine/edge/testing/' >> /etc/apk/repositories && \
    apk update && \
    apk add --no-cache dockerize

#set french timezone
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Europe/Paris /etc/localtime && \
    echo "Europe/Paris" >  /etc/timezone && \
    apk del tzdata

COPY --from=builder /app/dist/mee-conseiller /usr/share/nginx/html

COPY conf /etc/nginx
COPY conf/nginx.conf  /etc/nginx/conf.d/


WORKDIR /usr/share/nginx/html
# On renomme les fichiers main*.js générés en .tmpl
RUN export NOM_DU_FICHIER_MAIN=$(ls main*.js) &&  cp ${NOM_DU_FICHIER_MAIN} ${NOM_DU_FICHIER_MAIN}.tmpl

RUN echo "daemon off;" >> /etc/nginx/nginx.conf

# Au démarrage du conteneur, on dockerize les fichiers main*.js.tmpl avec les variables d'environnement adéquates
CMD export NOM_DU_FICHIER_MAIN=$(ls main*js) \
    && dockerize -delims "<%:%>" -template ${NOM_DU_FICHIER_MAIN}.tmpl:${NOM_DU_FICHIER_MAIN} nginx
