/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Prueba1TestModule } from '../../../test.module';
import { ProcesoCargaMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix-dialog.component';
import { ProcesoCargaMySuffixService } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix.service';
import { ProcesoCargaMySuffix } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix.model';
import { ZonaCargaMySuffixService } from '../../../../../../main/webapp/app/entities/zona-carga-my-suffix';

describe('Component Tests', () => {

    describe('ProcesoCargaMySuffix Management Dialog Component', () => {
        let comp: ProcesoCargaMySuffixDialogComponent;
        let fixture: ComponentFixture<ProcesoCargaMySuffixDialogComponent>;
        let service: ProcesoCargaMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [ProcesoCargaMySuffixDialogComponent],
                providers: [
                    ZonaCargaMySuffixService,
                    ProcesoCargaMySuffixService
                ]
            })
            .overrideTemplate(ProcesoCargaMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcesoCargaMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcesoCargaMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ProcesoCargaMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.procesoCarga = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'procesoCargaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ProcesoCargaMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.procesoCarga = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'procesoCargaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
