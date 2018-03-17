import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LogCargaMySuffix } from './log-carga-my-suffix.model';
import { LogCargaMySuffixService } from './log-carga-my-suffix.service';

@Component({
    selector: 'jhi-log-carga-my-suffix-detail',
    templateUrl: './log-carga-my-suffix-detail.component.html'
})
export class LogCargaMySuffixDetailComponent implements OnInit, OnDestroy {

    logCarga: LogCargaMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private logCargaService: LogCargaMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLogCargas();
    }

    load(id) {
        this.logCargaService.find(id)
            .subscribe((logCargaResponse: HttpResponse<LogCargaMySuffix>) => {
                this.logCarga = logCargaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLogCargas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'logCargaListModification',
            (response) => this.load(this.logCarga.id)
        );
    }
}
