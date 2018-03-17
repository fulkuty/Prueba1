import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LogCargaMySuffixComponent } from './log-carga-my-suffix.component';
import { LogCargaMySuffixDetailComponent } from './log-carga-my-suffix-detail.component';
import { LogCargaMySuffixPopupComponent } from './log-carga-my-suffix-dialog.component';
import { LogCargaMySuffixDeletePopupComponent } from './log-carga-my-suffix-delete-dialog.component';

export const logCargaRoute: Routes = [
    {
        path: 'log-carga-my-suffix',
        component: LogCargaMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.logCarga.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'log-carga-my-suffix/:id',
        component: LogCargaMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.logCarga.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const logCargaPopupRoute: Routes = [
    {
        path: 'log-carga-my-suffix-new',
        component: LogCargaMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.logCarga.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'log-carga-my-suffix/:id/edit',
        component: LogCargaMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.logCarga.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'log-carga-my-suffix/:id/delete',
        component: LogCargaMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.logCarga.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
