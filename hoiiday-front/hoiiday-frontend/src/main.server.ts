import { bootstrapApplication } from '@angular/platform-browser';
import { mergeApplicationConfig, ApplicationConfig } from '@angular/core';
import { provideServerRendering } from '@angular/platform-server';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

const bootstrap = () => bootstrapApplication(AppComponent, appConfig);


const serverConfig: ApplicationConfig = {
  providers: [
    provideServerRendering()
  ]
};

export const config = mergeApplicationConfig(appConfig, serverConfig);
export default bootstrap;
