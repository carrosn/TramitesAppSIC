import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TramitesAppTestModule } from '../../../test.module';
import { TipoPersonaComponent } from 'app/entities/tipo-persona/tipo-persona.component';
import { TipoPersonaService } from 'app/entities/tipo-persona/tipo-persona.service';
import { TipoPersona } from 'app/shared/model/tipo-persona.model';

describe('Component Tests', () => {
  describe('TipoPersona Management Component', () => {
    let comp: TipoPersonaComponent;
    let fixture: ComponentFixture<TipoPersonaComponent>;
    let service: TipoPersonaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [TipoPersonaComponent],
      })
        .overrideTemplate(TipoPersonaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoPersonaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoPersonaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoPersona(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoPersonas && comp.tipoPersonas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
