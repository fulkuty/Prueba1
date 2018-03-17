import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ZonaCargaMySuffix } from './zona-carga-my-suffix.model';
import { ZonaCargaMySuffixService } from './zona-carga-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-zona-carga-my-suffix',
    templateUrl: './zona-carga-my-suffix.component.html'
})
export class ZonaCargaMySuffixComponent implements OnInit, OnDestroy {
zonaCargas: ZonaCargaMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private zonaCargaService: ZonaCargaMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.zonaCargaService.query().subscribe(
            (res: HttpResponse<ZonaCargaMySuffix[]>) => {
                this.zonaCargas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInZonaCargas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ZonaCargaMySuffix) {
        return item.id;
    }
    registerChangeInZonaCargas() {
        this.eventSubscriber = this.eventManager.subscribe('zonaCargaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
