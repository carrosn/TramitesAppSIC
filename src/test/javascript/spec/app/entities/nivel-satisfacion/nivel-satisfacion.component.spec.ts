import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TramitesAppTestModule } from '../../../test.module';
import { NivelSatisfacionComponent } from 'app/entities/nivel-satisfacion/nivel-satisfacion.component';
import { NivelSatisfacionService } from 'app/entities/nivel-satisfacion/nivel-satisfacion.service';
import { NivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';

describe('Component Tests', () => {
  describe('NivelSatisfacion Management Component', () => {
    let comp: NivelSatisfacionComponent;
    let fixture: ComponentFixture<NivelSatisfacionComponent>;
    let service: NivelSatisfacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [NivelSatisfacionComponent],
      })
        .overrideTemplate(NivelSatisfacionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NivelSatisfacionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NivelSatisfacionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NivelSatisfacion(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.nivelSatisfacions && comp.nivelSatisfacions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
