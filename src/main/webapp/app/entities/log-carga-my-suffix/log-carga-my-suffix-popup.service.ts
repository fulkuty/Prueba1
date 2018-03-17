import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { LogCargaMySuffix } from './log-carga-my-suffix.model';
import { LogCargaMySuffixService } from './log-carga-my-suffix.service';

@Injectable()
export class LogCargaMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private logCargaService: LogCargaMySuffixService

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
                this.logCargaService.find(id)
                    .subscribe((logCargaResponse: HttpResponse<LogCargaMySuffix>) => {
                        const logCarga: LogCargaMySuffix = logCargaResponse.body;
                        logCarga.time = this.datePipe
                            .transform(logCarga.time, 'yyyy-MM-ddTHH:mm:ss');
                        logCarga.programado = this.datePipe
                            .transform(logCarga.programado, 'yyyy-MM-ddTHH:mm:ss');
                        logCarga.inicio = this.datePipe
                            .transform(logCarga.inicio, 'yyyy-MM-ddTHH:mm:ss');
                        logCarga.fin = this.datePipe
                            .transform(logCarga.fin, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.logCargaModalRef(component, logCarga);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.logCargaModalRef(component, new LogCargaMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    logCargaModalRef(component: Component, logCarga: LogCargaMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.logCarga = logCarga;
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
