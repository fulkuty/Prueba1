import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Prueba1SharedModule } from '../../shared';
import {
    ZonaCargaMySuffixService,
    ZonaCargaMySuffixPopupService,
    ZonaCargaMySuffixComponent,
    ZonaCargaMySuffixDetailComponent,
    ZonaCargaMySuffixDialogComponent,
    ZonaCargaMySuffixPopupComponent,
    ZonaCargaMySuffixDeletePopupComponent,
    ZonaCargaMySuffixDeleteDialogComponent,
    zonaCargaRoute,
    zonaCargaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...zonaCargaRoute,
    ...zonaCargaPopupRoute,
];

@NgModule({
    imports: [
        Prueba1SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ZonaCargaMySuffixComponent,
        ZonaCargaMySuffixDetailComponent,
        ZonaCargaMySuffixDialogComponent,
        ZonaCargaMySuffixDeleteDialogComponent,
        ZonaCargaMySuffixPopupComponent,
        ZonaCargaMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ZonaCargaMySuffixComponent,
        ZonaCargaMySuffixDialogComponent,
        ZonaCargaMySuffixPopupComponent,
        ZonaCargaMySuffixDeleteDialogComponent,
        ZonaCargaMySuffixDeletePopupComponent,
    ],
    providers: [
        ZonaCargaMySuffixService,
        ZonaCargaMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Prueba1ZonaCargaMySuffixModule {}
