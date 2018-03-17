import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LogCargaMySuffix } from './log-carga-my-suffix.model';
import { LogCargaMySuffixPopupService } from './log-carga-my-suffix-popup.service';
import { LogCargaMySuffixService } from './log-carga-my-suffix.service';
import { ZonaCargaMySuffix, ZonaCargaMySuffixService } from '../zona-carga-my-suffix';
import { ProcesoCargaMySuffix, ProcesoCargaMySuffixService } from '../proceso-carga-my-suffix';

@Component({
    selector: 'jhi-log-carga-my-suffix-dialog',
    templateUrl: './log-carga-my-suffix-dialog.component.html'
})
export class LogCargaMySuffixDialogComponent implements OnInit {

    logCarga: LogCargaMySuffix;
    isSaving: boolean;

    zonas: ZonaCargaMySuffix[];

    procesocargas: ProcesoCargaMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private logCargaService: LogCargaMySuffixService,
        private zonaCargaService: ZonaCargaMySuffixService,
        private procesoCargaService: ProcesoCargaMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.zonaCargaService
            .query({filter: 'logcarga-is-null'})
            .subscribe((res: HttpResponse<ZonaCargaMySuffix[]>) => {
                if (!this.logCarga.zonaId) {
                    this.zonas = res.body;
                } else {
                    this.zonaCargaService
                        .find(this.logCarga.zonaId)
                        .subscribe((subRes: HttpResponse<ZonaCargaMySuffix>) => {
                            this.zonas = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.procesoCargaService.query()
            .subscribe((res: HttpResponse<ProcesoCargaMySuffix[]>) => { this.procesocargas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.logCarga.id !== undefined) {
            this.subscribeToSaveResponse(
                this.logCargaService.update(this.logCarga));
        } else {
            this.subscribeToSaveResponse(
                this.logCargaService.create(this.logCarga));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LogCargaMySuffix>>) {
        result.subscribe((res: HttpResponse<LogCargaMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LogCargaMySuffix) {
        this.eventManager.broadcast({ name: 'logCargaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackZonaCargaById(index: number, item: ZonaCargaMySuffix) {
        return item.id;
    }

    trackProcesoCargaById(index: number, item: ProcesoCargaMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-log-carga-my-suffix-popup',
    template: ''
})
export class LogCargaMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logCargaPopupService: LogCargaMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.logCargaPopupService
                    .open(LogCargaMySuffixDialogComponent as Component, params['id']);
            } else {
                this.logCargaPopupService
                    .open(LogCargaMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
