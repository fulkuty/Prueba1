/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Prueba1TestModule } from '../../../test.module';
import { ProcesoCargaMySuffixComponent } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix.component';
import { ProcesoCargaMySuffixService } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix.service';
import { ProcesoCargaMySuffix } from '../../../../../../main/webapp/app/entities/proceso-carga-my-suffix/proceso-carga-my-suffix.model';

describe('Component Tests', () => {

    describe('ProcesoCargaMySuffix Management Component', () => {
        let comp: ProcesoCargaMySuffixComponent;
        let fixture: ComponentFixture<ProcesoCargaMySuffixComponent>;
        let service: ProcesoCargaMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [ProcesoCargaMySuffixComponent],
                providers: [
                    ProcesoCargaMySuffixService
                ]
            })
            .overrideTemplate(ProcesoCargaMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcesoCargaMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcesoCargaMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ProcesoCargaMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.procesoCargas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
