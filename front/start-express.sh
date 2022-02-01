#!/bin/sh

export NOM_DU_FICHIER_MAIN1=$(ls /app/dist/mee-candidat/browser/main-es5*js)
export NOM_DU_FICHIER_MAIN2=$(ls /app/dist/mee-candidat/browser/main-es2015*js)
export NOM_DU_FICHIER_MAIN3=$(ls /app/dist/mee-candidat/server/main.js)
dockerize -delims "<%:%>" -template ${NOM_DU_FICHIER_MAIN1}.tmpl:${NOM_DU_FICHIER_MAIN1} \
-template ${NOM_DU_FICHIER_MAIN2}.tmpl:${NOM_DU_FICHIER_MAIN2} \
-template ${NOM_DU_FICHIER_MAIN3}.tmpl:${NOM_DU_FICHIER_MAIN3} npm run serve:ssr
