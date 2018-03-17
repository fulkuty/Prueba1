/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Prueba1TestModule } from '../../../test.module';
import { LogCargaMySuffixComponent } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix.component';
import { LogCargaMySuffixService } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix.service';
import { LogCargaMySuffix } from '../../../../../../main/webapp/app/entities/log-carga-my-suffix/log-carga-my-suffix.model';

describe('Component Tests', () => {

    describe('LogCargaMySuffix Management Component', () => {
        let comp: LogCargaMySuffixComponent;
        let fixture: ComponentFixture<LogCargaMySuffixComponent>;
        let service: LogCargaMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [LogCargaMySuffixComponent],
                providers: [
                    LogCargaMySuffixService
                ]
            })
            .overrideTemplate(LogCargaMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogCargaMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogCargaMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LogCargaMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.logCargas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
