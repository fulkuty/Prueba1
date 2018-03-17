import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ZonaCargaMySuffix } from './zona-carga-my-suffix.model';
import { ZonaCargaMySuffixPopupService } from './zona-carga-my-suffix-popup.service';
import { ZonaCargaMySuffixService } from './zona-carga-my-suffix.service';

@Component({
    selector: 'jhi-zona-carga-my-suffix-dialog',
    templateUrl: './zona-carga-my-suffix-dialog.component.html'
})
export class ZonaCargaMySuffixDialogComponent implements OnInit {

    zonaCarga: ZonaCargaMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private zonaCargaService: ZonaCargaMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.zonaCarga.id !== undefined) {
            this.subscribeToSaveResponse(
                this.zonaCargaService.update(this.zonaCarga));
        } else {
            this.subscribeToSaveResponse(
                this.zonaCargaService.create(this.zonaCarga));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ZonaCargaMySuffix>>) {
        result.subscribe((res: HttpResponse<ZonaCargaMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ZonaCargaMySuffix) {
        this.eventManager.broadcast({ name: 'zonaCargaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-zona-carga-my-suffix-popup',
    template: ''
})
export class ZonaCargaMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zonaCargaPopupService: ZonaCargaMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.zonaCargaPopupService
                    .open(ZonaCargaMySuffixDialogComponent as Component, params['id']);
            } else {
                this.zonaCargaPopupService
                    .open(ZonaCargaMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
