import 'zone.js/dist/zone-node';

import {ngExpressEngine} from '@nguniversal/express-engine';
import * as express from 'express';
import {join} from 'path';

import {AppServerModule} from './src/main.server';
import {APP_BASE_HREF} from '@angular/common';
import {existsSync} from 'fs';
import * as compression from 'compression';
import * as cookieparser from 'cookie-parser';
import * as cors from 'cors';
import {environment} from './src/environments/environment';
import axios from 'axios';
import * as bodyParser from 'body-parser';
import 'localstorage-polyfill';


// The Express app is exported so that it can be used by serverless Functions.
export function app(): express.Express {
    const server = express();
    const distFolder = join(process.cwd(), 'dist/pe-actions-candidat/browser');
    const indexHtml = existsSync(join(distFolder, 'index.original.html')) ? 'index.original.html' : 'index';

    // Our Universal express-engine (found @ https://github.com/angular/universal/tree/master/modules/express-engine)
    server.engine('html', ngExpressEngine({
        bootstrap: AppServerModule,
    }));

    server.set('view engine', 'html');
    server.set('views', distFolder);


    server.get('/robots.txt', async (req, res) => {
        res.type('text/plain');
        if (environment.cadre !== 'production') {
            res.send('User-agent: *\nDisallow: /');
        } else {
            res.send('User-agent: *');
        }
    });

    server.get('/evenement/all/:codeDpt/:pageNo', async (req, res) => {
       const  dept = `${req.params.codeDpt}`;
       let url = `${environment.peactionsDomainePath}/evenement/all?etat=publier&pageNo=${req.params.pageNo}`;
       if (dept && dept !== null && dept !== 'null' && dept !== 'undefined'){
           url = url + '&dept=' + dept;
        }
       await axios.get(encodeURI(url)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.get('/evenement/count/all/:codeDpt', async (req, res) => {
        const  dept = `${req.params.codeDpt}`;
        let url = `${environment.peactionsDomainePath}/evenement/count/all?etat=publier`;
        if (dept && dept !== null && dept !== 'null' && dept !== 'undefined'){
            url = url + '&dept=' + dept;
        }
        await axios.get(encodeURI(url)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.get('/evenement/detail/:eventId', async (req, res) => {
        await axios.get(encodeURI(`${environment.peactionsDomainePath}/evenement/detail/${req.params.eventId}`)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.get('/esd/offre/:offreId', async (req, res) => {
        await axios.get(encodeURI(`${environment.peactionsDomainePath}/esd/offre/${req.params.offreId}`)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.get('/modaliteAcces/all/evenement/:eventId', async (req, res) => {
        await axios.get(encodeURI(`${environment.peactionsDomainePath}/modaliteAcces/all/evenement/${req.params.eventId}`)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.get('/modaliteAcces/all', async (req, res) => {
        await axios.get(encodeURI(`${environment.peactionsDomainePath}/modaliteAcces/all`)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });


    server.get('/departement/all', async (req, res) => {
        await axios.get(encodeURI(`${environment.peactionsDomainePath}/departement/all`)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.get('/prerequis/all/:prerequisId', async (req, res) => {
        await axios.get(encodeURI(`${environment.peactionsDomainePath}/prerequis/all/${req.params.prerequisId}`)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.get('/candidat/all/evenement/:eventId', async (req, res) => {
        await axios.get(encodeURI(`${environment.peactionsDomainePath}/candidat/all/evenement/${req.params.eventId}?avecPrerequisValider=true`)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.get('/tag/tags/type/:typeId', async (req, res) => {
        await axios.get(encodeURI(`${environment.peactionsDomainePath}/tag/tags/type/${req.params.typeId}`)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.get('/evenement/all/departement/:departementId', async (req, res) => {
        await axios.get(encodeURI(`${environment.peactionsDomainePath}/evenement/all/departement/${req.params.departementId}`)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    const jsonParser = bodyParser.json();

    server.get('/candidat/inscrit/:evenementId/:candidatId', jsonParser, async (req, res) => {
        await axios.get(encodeURI(`${environment.peactionsDomainePath}/candidat/inscrit/?evenementId=${req.params.evenementId}&candidatId=${req.params.candidatId}`)).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    const urlencodedParser = bodyParser.urlencoded({extended: false});

    server.post('/candidat/evenement/:eventId', jsonParser, async (req, res) => {
        const url = `${environment.peactionsDomainePath}/candidat/evenement/?evenementId=${req.params.eventId}`;
        await axios.post(encodeURI(url), req.body).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.put('/candidat/evenement/:eventId', jsonParser, async (req, res) => {
        const url = `${environment.peactionsDomainePath}/candidat/evenement?evenementId=${req.params.eventId}`;
        await axios.put(encodeURI(url), req.body).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    server.post('/candidat/authentifier', jsonParser, async (req, res) => {
        const url = `${environment.peactionsDomainePath}/candidat/authentifier`;
        await axios.post(encodeURI(url), req.body).then(response => {
            res.status(200).send(response.data);
        }).catch((error) => {
            res.status(error.response.status).json({error: error.toString()});
        });
    });

    // Serve static files from /browser
    server.get('*.*', express.static(distFolder, {
        maxAge: '1y'
    }));

    // All regular routes use the Universal engine
    server.get('*', (req, res) => {
        res.render(indexHtml, {req, providers: [{provide: APP_BASE_HREF, useValue: req.baseUrl}]});
    });

    return server;
}


function run(): void {
    const port = process.env.PORT || 4000;

    // Start up the Node server
    const server = app();

    const helmet = require('helmet');

    server.use(helmet());
    // Add cors
    server.use(cors());
    // gzip
    server.use(compression());
    // cokies
    server.use(cookieparser());
    server.use(express.urlencoded({extended: true}));
    server.disable('x-powered-by');
    server.disable('server');
    server.set('Content-Security-Policy', 'default-src ‘self‘; img-src ‘self’; frame-src ‘none;’');
    server.set('X-Frame-Option', 'SAMEORIGIN');
    server.set('X-Content-Type-Options', 'nosniff');
    server.set('Strict-Transport-Security', 'max-age=31536000; includeSubDomains; preload');

    server.listen(port, () => {
        // console.log(`Node Express server listening on http://localhost:${port}`);
    });
}

// Webpack will replace 'require' with '__webpack_require__'
// '__non_webpack_require__' is a proxy to Node 'require'
// The below code is to ensure that the server is run only when not requiring the bundle.
declare const __non_webpack_require__: NodeRequire;
const mainModule = __non_webpack_require__.main;
const moduleFilename = mainModule && mainModule.filename || '';
if (moduleFilename === __filename || moduleFilename.includes('iisnode')) {
    run();
}

export * from './src/main.server';
