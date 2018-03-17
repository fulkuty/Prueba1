import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProcesoCargaMySuffix } from './proceso-carga-my-suffix.model';
import { ProcesoCargaMySuffixPopupService } from './proceso-carga-my-suffix-popup.service';
import { ProcesoCargaMySuffixService } from './proceso-carga-my-suffix.service';
import { ZonaCargaMySuffix, ZonaCargaMySuffixService } from '../zona-carga-my-suffix';

@Component({
    selector: 'jhi-proceso-carga-my-suffix-dialog',
    templateUrl: './proceso-carga-my-suffix-dialog.component.html'
})
export class ProcesoCargaMySuffixDialogComponent implements OnInit {

    procesoCarga: ProcesoCargaMySuffix;
    isSaving: boolean;

    zonas: ZonaCargaMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private procesoCargaService: ProcesoCargaMySuffixService,
        private zonaCargaService: ZonaCargaMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.zonaCargaService
            .query({filter: 'procesocarga-is-null'})
            .subscribe((res: HttpResponse<ZonaCargaMySuffix[]>) => {
                if (!this.procesoCarga.zonaId) {
                    this.zonas = res.body;
                } else {
                    this.zonaCargaService
                        .find(this.procesoCarga.zonaId)
                        .subscribe((subRes: HttpResponse<ZonaCargaMySuffix>) => {
                            this.zonas = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.procesoCarga.id !== undefined) {
            this.subscribeToSaveResponse(
                this.procesoCargaService.update(this.procesoCarga));
        } else {
            this.subscribeToSaveResponse(
                this.procesoCargaService.create(this.procesoCarga));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ProcesoCargaMySuffix>>) {
        result.subscribe((res: HttpResponse<ProcesoCargaMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ProcesoCargaMySuffix) {
        this.eventManager.broadcast({ name: 'procesoCargaListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-proceso-carga-my-suffix-popup',
    template: ''
})
export class ProcesoCargaMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private procesoCargaPopupService: ProcesoCargaMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.procesoCargaPopupService
                    .open(ProcesoCargaMySuffixDialogComponent as Component, params['id']);
            } else {
                this.procesoCargaPopupService
                    .open(ProcesoCargaMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
