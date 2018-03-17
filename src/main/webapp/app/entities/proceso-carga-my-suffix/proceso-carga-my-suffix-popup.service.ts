import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ProcesoCargaMySuffix } from './proceso-carga-my-suffix.model';
import { ProcesoCargaMySuffixService } from './proceso-carga-my-suffix.service';

@Injectable()
export class ProcesoCargaMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private procesoCargaService: ProcesoCargaMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.procesoCargaService.find(id)
                    .subscribe((procesoCargaResponse: HttpResponse<ProcesoCargaMySuffix>) => {
                        const procesoCarga: ProcesoCargaMySuffix = procesoCargaResponse.body;
                        procesoCarga.programado = this.datePipe
                            .transform(procesoCarga.programado, 'yyyy-MM-ddTHH:mm:ss');
                        procesoCarga.inicio = this.datePipe
                            .transform(procesoCarga.inicio, 'yyyy-MM-ddTHH:mm:ss');
                        procesoCarga.fin = this.datePipe
                            .transform(procesoCarga.fin, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.procesoCargaModalRef(component, procesoCarga);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.procesoCargaModalRef(component, new ProcesoCargaMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    procesoCargaModalRef(component: Component, procesoCarga: ProcesoCargaMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.procesoCarga = procesoCarga;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
