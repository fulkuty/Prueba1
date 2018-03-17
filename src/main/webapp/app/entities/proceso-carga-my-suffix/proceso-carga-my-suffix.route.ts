import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ProcesoCargaMySuffixComponent } from './proceso-carga-my-suffix.component';
import { ProcesoCargaMySuffixDetailComponent } from './proceso-carga-my-suffix-detail.component';
import { ProcesoCargaMySuffixPopupComponent } from './proceso-carga-my-suffix-dialog.component';
import { ProcesoCargaMySuffixDeletePopupComponent } from './proceso-carga-my-suffix-delete-dialog.component';

export const procesoCargaRoute: Routes = [
    {
        path: 'proceso-carga-my-suffix',
        component: ProcesoCargaMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.procesoCarga.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'proceso-carga-my-suffix/:id',
        component: ProcesoCargaMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.procesoCarga.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const procesoCargaPopupRoute: Routes = [
    {
        path: 'proceso-carga-my-suffix-new',
        component: ProcesoCargaMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.procesoCarga.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'proceso-carga-my-suffix/:id/edit',
        component: ProcesoCargaMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.procesoCarga.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'proceso-carga-my-suffix/:id/delete',
        component: ProcesoCargaMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.procesoCarga.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
