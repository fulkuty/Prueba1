/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Prueba1TestModule } from '../../../test.module';
import { LogCargaMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix-delete-dialog.component';
import { LogCargaMySuffixService } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix.service';

describe('Component Tests', () => {

    describe('LogCargaMySuffix Management Delete Component', () => {
        let comp: LogCargaMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<LogCargaMySuffixDeleteDialogComponent>;
        let service: LogCargaMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [LogCargaMySuffixDeleteDialogComponent],
                providers: [
                    LogCargaMySuffixService
                ]
            })
            .overrideTemplate(LogCargaMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogCargaMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogCargaMySuffixService);
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
