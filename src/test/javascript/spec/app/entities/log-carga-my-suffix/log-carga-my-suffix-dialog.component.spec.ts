/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Prueba1TestModule } from '../../../test.module';
import { LogCargaMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix-dialog.component';
import { LogCargaMySuffixService } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix.service';
import { LogCargaMySuffix } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix.model';
import { ZonaCargaMySuffixService } from '../../../../../../main/webapp/app/entities/zona-carga-my-suffix';
import { ProcesoCargaMySuffixService } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix';

describe('Component Tests', () => {

    describe('LogCargaMySuffix Management Dialog Component', () => {
        let comp: LogCargaMySuffixDialogComponent;
        let fixture: ComponentFixture<LogCargaMySuffixDialogComponent>;
        let service: LogCargaMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [LogCargaMySuffixDialogComponent],
                providers: [
                    ZonaCargaMySuffixService,
                    ProcesoCargaMySuffixService,
                    LogCargaMySuffixService
                ]
            })
            .overrideTemplate(LogCargaMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogCargaMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogCargaMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LogCargaMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.logCarga = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'logCargaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LogCargaMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.logCarga = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'logCargaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
