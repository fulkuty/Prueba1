/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Prueba1TestModule } from '../../../test.module';
import { ZonaCargaMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/zona-carga-my-suffix/zona-carga-my-suffix-delete-dialog.component';
import { ZonaCargaMySuffixService } from '../../../../../../main/webapp/app/entities/zona-carga-my-suffix/zona-carga-my-suffix.service';

describe('Component Tests', () => {

    describe('ZonaCargaMySuffix Management Delete Component', () => {
        let comp: ZonaCargaMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<ZonaCargaMySuffixDeleteDialogComponent>;
        let service: ZonaCargaMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [ZonaCargaMySuffixDeleteDialogComponent],
                providers: [
                    ZonaCargaMySuffixService
                ]
            })
            .overrideTemplate(ZonaCargaMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ZonaCargaMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZonaCargaMySuffixService);
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
