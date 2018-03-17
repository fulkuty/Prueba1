import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Prueba1SharedModule } from '../../shared';
import {
    LogCargaMySuffixService,
    LogCargaMySuffixPopupService,
    LogCargaMySuffixComponent,
    LogCargaMySuffixDetailComponent,
    LogCargaMySuffixDialogComponent,
    LogCargaMySuffixPopupComponent,
    LogCargaMySuffixDeletePopupComponent,
    LogCargaMySuffixDeleteDialogComponent,
    logCargaRoute,
    logCargaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...logCargaRoute,
    ...logCargaPopupRoute,
];

@NgModule({
    imports: [
        Prueba1SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LogCargaMySuffixComponent,
        LogCargaMySuffixDetailComponent,
        LogCargaMySuffixDialogComponent,
        LogCargaMySuffixDeleteDialogComponent,
        LogCargaMySuffixPopupComponent,
        LogCargaMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        LogCargaMySuffixComponent,
        LogCargaMySuffixDialogComponent,
        LogCargaMySuffixPopupComponent,
        LogCargaMySuffixDeleteDialogComponent,
        LogCargaMySuffixDeletePopupComponent,
    ],
    providers: [
        LogCargaMySuffixService,
        LogCargaMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Prueba1LogCargaMySuffixModule {}
