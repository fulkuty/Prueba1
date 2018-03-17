import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ProcesoCargaMySuffix } from './proceso-carga-my-suffix.model';
import { ProcesoCargaMySuffixService } from './proceso-carga-my-suffix.service';

@Component({
    selector: 'jhi-proceso-carga-my-suffix-detail',
    templateUrl: './proceso-carga-my-suffix-detail.component.html'
})
export class ProcesoCargaMySuffixDetailComponent implements OnInit, OnDestroy {

    procesoCarga: ProcesoCargaMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private procesoCargaService: ProcesoCargaMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProcesoCargas();
    }

    load(id) {
        this.procesoCargaService.find(id)
            .subscribe((procesoCargaResponse: HttpResponse<ProcesoCargaMySuffix>) => {
                this.procesoCarga = procesoCargaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProcesoCargas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'procesoCargaListModification',
            (response) => this.load(this.procesoCarga.id)
        );
    }
}
