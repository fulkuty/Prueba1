import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Prueba1SharedModule } from '../../shared';
import {
    ProcesoCargaMySuffixService,
    ProcesoCargaMySuffixPopupService,
    ProcesoCargaMySuffixComponent,
    ProcesoCargaMySuffixDetailComponent,
    ProcesoCargaMySuffixDialogComponent,
    ProcesoCargaMySuffixPopupComponent,
    ProcesoCargaMySuffixDeletePopupComponent,
    ProcesoCargaMySuffixDeleteDialogComponent,
    procesoCargaRoute,
    procesoCargaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...procesoCargaRoute,
    ...procesoCargaPopupRoute,
];

@NgModule({
    imports: [
        Prueba1SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProcesoCargaMySuffixComponent,
        ProcesoCargaMySuffixDetailComponent,
        ProcesoCargaMySuffixDialogComponent,
        ProcesoCargaMySuffixDeleteDialogComponent,
        ProcesoCargaMySuffixPopupComponent,
        ProcesoCargaMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ProcesoCargaMySuffixComponent,
        ProcesoCargaMySuffixDialogComponent,
        ProcesoCargaMySuffixPopupComponent,
        ProcesoCargaMySuffixDeleteDialogComponent,
        ProcesoCargaMySuffixDeletePopupComponent,
    ],
    providers: [
        ProcesoCargaMySuffixService,
        ProcesoCargaMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Prueba1ProcesoCargaMySuffixModule {}
