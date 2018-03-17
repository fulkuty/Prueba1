/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Prueba1TestModule } from '../../../test.module';
import { ZonaCargaMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/zona-carga-my-suffix/zona-carga-my-suffix-detail.component';
import { ZonaCargaMySuffixService } from '../../../../../../main/webapp/app/entities/zona-carga-my-suffix/zona-carga-my-suffix.service';
import { ZonaCargaMySuffix } from '../../../../../../main/webapp/app/entities/zona-carga-my-suffix/zona-carga-my-suffix.model';

describe('Component Tests', () => {

    describe('ZonaCargaMySuffix Management Detail Component', () => {
        let comp: ZonaCargaMySuffixDetailComponent;
        let fixture: ComponentFixture<ZonaCargaMySuffixDetailComponent>;
        let service: ZonaCargaMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Prueba1TestModule],
                declarations: [ZonaCargaMySuffixDetailComponent],
                providers: [
                    ZonaCargaMySuffixService
                ]
            })
            .overrideTemplate(ZonaCargaMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ZonaCargaMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZonaCargaMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ZonaCargaMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.zonaCarga).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
