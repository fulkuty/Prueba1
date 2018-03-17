/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Prueba1TestModule } from '../../../test.module';
import { ProcesoCargaMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix-detail.component';
import { ProcesoCargaMySuffixService } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix.service';
import { ProcesoCargaMySuffix } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix.model';

describe('Component Tests', () => {

    describe('ProcesoCargaMySuffix Management Detail Component', () => {
        let comp: ProcesoCargaMySuffixDetailComponent;
        let fixture: ComponentFixture<ProcesoCargaMySuffixDetailComponent>;
        let service: ProcesoCargaMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [ProcesoCargaMySuffixDetailComponent],
                providers: [
                    ProcesoCargaMySuffixService
                ]
            })
            .overrideTemplate(ProcesoCargaMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcesoCargaMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcesoCargaMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ProcesoCargaMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.procesoCarga).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
