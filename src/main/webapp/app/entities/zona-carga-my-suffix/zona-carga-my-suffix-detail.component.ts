import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ZonaCargaMySuffix } from './zona-carga-my-suffix.model';
import { ZonaCargaMySuffixService } from './zona-carga-my-suffix.service';

@Component({
    selector: 'jhi-zona-carga-my-suffix-detail',
    templateUrl: './zona-carga-my-suffix-detail.component.html'
})
export class ZonaCargaMySuffixDetailComponent implements OnInit, OnDestroy {

    zonaCarga: ZonaCargaMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private zonaCargaService: ZonaCargaMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInZonaCargas();
    }

    load(id) {
        this.zonaCargaService.find(id)
            .subscribe((zonaCargaResponse: HttpResponse<ZonaCargaMySuffix>) => {
                this.zonaCarga = zonaCargaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInZonaCargas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'zonaCargaListModification',
            (response) => this.load(this.zonaCarga.id)
        );
    }
}
