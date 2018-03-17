import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LogCargaMySuffix } from './log-carga-my-suffix.model';
import { LogCargaMySuffixPopupService } from './log-carga-my-suffix-popup.service';
import { LogCargaMySuffixService } from './log-carga-my-suffix.service';

@Component({
    selector: 'jhi-log-carga-my-suffix-delete-dialog',
    templateUrl: './log-carga-my-suffix-delete-dialog.component.html'
})
export class LogCargaMySuffixDeleteDialogComponent {

    logCarga: LogCargaMySuffix;

    constructor(
        private logCargaService: LogCargaMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.logCargaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'logCargaListModification',
                content: 'Deleted an logCarga'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-log-carga-my-suffix-delete-popup',
    template: ''
})
export class LogCargaMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logCargaPopupService: LogCargaMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.logCargaPopupService
                .open(LogCargaMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
