// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,

  peactonsDomainePath: 'http://localhost:8080/peactions/v1',
// Path recette
//  peactonsDomainePath: 'http://192.168.4.193:8080/peactions/v1',
  urlTagCommander: 'cdn.tagcommander.com/5894/uat/tc_peAction_31.js',
  emailContact: 'peactions.00324@pole-emploi.fr'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
