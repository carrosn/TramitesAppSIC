import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TramitesAppTestModule } from '../../../test.module';
import { TipoDocumentoIdentificacionComponent } from 'app/entities/tipo-documento-identificacion/tipo-documento-identificacion.component';
import { TipoDocumentoIdentificacionService } from 'app/entities/tipo-documento-identificacion/tipo-documento-identificacion.service';
import { TipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';

describe('Component Tests', () => {
  describe('TipoDocumentoIdentificacion Management Component', () => {
    let comp: TipoDocumentoIdentificacionComponent;
    let fixture: ComponentFixture<TipoDocumentoIdentificacionComponent>;
    let service: TipoDocumentoIdentificacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [TipoDocumentoIdentificacionComponent],
      })
        .overrideTemplate(TipoDocumentoIdentificacionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoDocumentoIdentificacionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoDocumentoIdentificacionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoDocumentoIdentificacion(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoDocumentoIdentificacions && comp.tipoDocumentoIdentificacions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
