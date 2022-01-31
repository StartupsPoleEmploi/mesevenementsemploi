#build stage
FROM node:15.14.0-alpine as builder
WORKDIR app
COPY package.json .
RUN npm install
ADD . .
RUN npm run build:ssr:production

#run stage
FROM node:15.14.0-alpine
LABEL Author="Team Monstreuils"
LABEL Project="pe-actions-candidat"

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

WORKDIR /app

# Copy dependency definitions
COPY --from=builder /app/package.json ./
# Get all the code needed to run the app
COPY --from=builder /app/dist/ ./dist/
# Copy launch script
COPY --from=builder /app/start-express.sh ./
# Create node_module for external dependencies and update launch script chmod
RUN mkdir -p node_modules && chmod +x start-express.sh
# Create Dockerize template files
RUN export NOM_DU_FICHIER_MAIN1=$(ls /app/dist/pe-actions-candidat/browser/main-es5*js) &&  cp ${NOM_DU_FICHIER_MAIN1} ${NOM_DU_FICHIER_MAIN1}.tmpl
RUN export NOM_DU_FICHIER_MAIN2=$(ls /app/dist/pe-actions-candidat/browser/main-es2015*js) &&  cp ${NOM_DU_FICHIER_MAIN2} ${NOM_DU_FICHIER_MAIN2}.tmpl
RUN export NOM_DU_FICHIER_MAIN3=$(ls /app/dist/pe-actions-candidat/server/main.js) &&  cp ${NOM_DU_FICHIER_MAIN3} ${NOM_DU_FICHIER_MAIN3}.tmpl

# Expose the port the app runs in
EXPOSE 4000
# Dockerize and serve the app
CMD [ "./start-express.sh" ]

