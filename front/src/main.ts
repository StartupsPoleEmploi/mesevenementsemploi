import {enableProdMode} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

import {AppModule} from './app/app.module';
import {environment} from './environments/environment';

if (environment.production) {
  enableProdMode();
}
if (typeof environment.urlTagCommander !== 'undefined') {
  const tagContainers = document.createElement('script');
  tagContainers.setAttribute('type', 'text/javascript');
  tagContainers.setAttribute('src', '//' + environment.urlTagCommander);
  tagContainers.setAttribute('async', String(true));
  const tagCommandr = (document?.querySelector('head') || document?.querySelector('body'))?.appendChild(tagContainers);
}

document.addEventListener('DOMContentLoaded', () => {
  platformBrowserDynamic().bootstrapModule(AppModule)
      .catch(err => console.error(err));
});
