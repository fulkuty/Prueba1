/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Prueba1TestModule } from '../../../test.module';
import { LogCargaMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix-detail.component';
import { LogCargaMySuffixService } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix.service';
import { LogCargaMySuffix } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix.model';

describe('Component Tests', () => {

    describe('LogCargaMySuffix Management Detail Component', () => {
        let comp: LogCargaMySuffixDetailComponent;
        let fixture: ComponentFixture<LogCargaMySuffixDetailComponent>;
        let service: LogCargaMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [LogCargaMySuffixDetailComponent],
                providers: [
                    LogCargaMySuffixService
                ]
            })
            .overrideTemplate(LogCargaMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogCargaMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogCargaMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LogCargaMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.logCarga).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
