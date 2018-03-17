import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ZonaCargaMySuffixComponent } from './zona-carga-my-suffix.component';
import { ZonaCargaMySuffixDetailComponent } from './zona-carga-my-suffix-detail.component';
import { ZonaCargaMySuffixPopupComponent } from './zona-carga-my-suffix-dialog.component';
import { ZonaCargaMySuffixDeletePopupComponent } from './zona-carga-my-suffix-delete-dialog.component';

export const zonaCargaRoute: Routes = [
    {
        path: 'zona-carga-my-suffix',
        component: ZonaCargaMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.zonaCarga.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'zona-carga-my-suffix/:id',
        component: ZonaCargaMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.zonaCarga.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const zonaCargaPopupRoute: Routes = [
    {
        path: 'zona-carga-my-suffix-new',
        component: ZonaCargaMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.zonaCarga.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'zona-carga-my-suffix/:id/edit',
        component: ZonaCargaMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.zonaCarga.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'zona-carga-my-suffix/:id/delete',
        component: ZonaCargaMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prueba1App.zonaCarga.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
