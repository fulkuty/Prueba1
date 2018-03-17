/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Prueba1TestModule } from '../../../test.module';
import { ProcesoCargaMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix-delete-dialog.component';
import { ProcesoCargaMySuffixService } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix.service';

describe('Component Tests', () => {

    describe('ProcesoCargaMySuffix Management Delete Component', () => {
        let comp: ProcesoCargaMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<ProcesoCargaMySuffixDeleteDialogComponent>;
        let service: ProcesoCargaMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [ProcesoCargaMySuffixDeleteDialogComponent],
                providers: [
                    ProcesoCargaMySuffixService
                ]
            })
            .overrideTemplate(ProcesoCargaMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcesoCargaMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcesoCargaMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
