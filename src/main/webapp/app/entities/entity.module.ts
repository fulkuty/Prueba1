import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Prueba1ProcesoCargaMySuffixModule } from './proceso-carga-my-suffix/proceso-carga-my-suffix.module';
import { Prueba1LogCargaMySuffixModule } from './log-carga-my-suffix/log-carga-my-suffix.module';
import { Prueba1ZonaCargaMySuffixModule } from './zona-carga-my-suffix/zona-carga-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        Prueba1ProcesoCargaMySuffixModule,
        Prueba1LogCargaMySuffixModule,
        Prueba1ZonaCargaMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Prueba1EntityModule {}
