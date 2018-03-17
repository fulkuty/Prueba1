/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Prueba1TestModule } from '../../../test.module';
import { ZonaCargaMySuffixComponent } from '../../../../../../main/webapp/app/entities/zona-carga-my-suffix/zona-carga-my-suffix.component';
import { ZonaCargaMySuffixService } from '../../../../../../main/webapp/app/entities/zona-carga-my-suffix/zona-carga-my-suffix.service';
import { ZonaCargaMySuffix } from '../../../../../../main/webapp/app/entities/zona-carga-my-suffix/zona-carga-my-suffix.model';

describe('Component Tests', () => {

    describe('ZonaCargaMySuffix Management Component', () => {
        let comp: ZonaCargaMySuffixComponent;
        let fixture: ComponentFixture<ZonaCargaMySuffixComponent>;
        let service: ZonaCargaMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [ZonaCargaMySuffixComponent],
                providers: [
                    ZonaCargaMySuffixService
                ]
            })
            .overrideTemplate(ZonaCargaMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ZonaCargaMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZonaCargaMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ZonaCargaMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.zonaCargas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
