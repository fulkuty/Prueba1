import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProcesoCargaMySuffix } from './proceso-carga-my-suffix.model';
import { ProcesoCargaMySuffixPopupService } from './proceso-carga-my-suffix-popup.service';
import { ProcesoCargaMySuffixService } from './proceso-carga-my-suffix.service';

@Component({
    selector: 'jhi-proceso-carga-my-suffix-delete-dialog',
    templateUrl: './proceso-carga-my-suffix-delete-dialog.component.html'
})
export class ProcesoCargaMySuffixDeleteDialogComponent {

    procesoCarga: ProcesoCargaMySuffix;

    constructor(
        private procesoCargaService: ProcesoCargaMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.procesoCargaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'procesoCargaListModification',
                content: 'Deleted an procesoCarga'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-proceso-carga-my-suffix-delete-popup',
    template: ''
})
export class ProcesoCargaMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private procesoCargaPopupService: ProcesoCargaMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.procesoCargaPopupService
                .open(ProcesoCargaMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
