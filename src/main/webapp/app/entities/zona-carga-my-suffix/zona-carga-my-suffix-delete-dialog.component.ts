import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ZonaCargaMySuffix } from './zona-carga-my-suffix.model';
import { ZonaCargaMySuffixPopupService } from './zona-carga-my-suffix-popup.service';
import { ZonaCargaMySuffixService } from './zona-carga-my-suffix.service';

@Component({
    selector: 'jhi-zona-carga-my-suffix-delete-dialog',
    templateUrl: './zona-carga-my-suffix-delete-dialog.component.html'
})
export class ZonaCargaMySuffixDeleteDialogComponent {

    zonaCarga: ZonaCargaMySuffix;

    constructor(
        private zonaCargaService: ZonaCargaMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.zonaCargaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'zonaCargaListModification',
                content: 'Deleted an zonaCarga'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-zona-carga-my-suffix-delete-popup',
    template: ''
})
export class ZonaCargaMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zonaCargaPopupService: ZonaCargaMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.zonaCargaPopupService
                .open(ZonaCargaMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
