import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ZonaCargaMySuffix } from './zona-carga-my-suffix.model';
import { ZonaCargaMySuffixService } from './zona-carga-my-suffix.service';

@Injectable()
export class ZonaCargaMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private zonaCargaService: ZonaCargaMySuffixService

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
                this.zonaCargaService.find(id)
                    .subscribe((zonaCargaResponse: HttpResponse<ZonaCargaMySuffix>) => {
                        const zonaCarga: ZonaCargaMySuffix = zonaCargaResponse.body;
                        this.ngbModalRef = this.zonaCargaModalRef(component, zonaCarga);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.zonaCargaModalRef(component, new ZonaCargaMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    zonaCargaModalRef(component: Component, zonaCarga: ZonaCargaMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.zonaCarga = zonaCarga;
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
