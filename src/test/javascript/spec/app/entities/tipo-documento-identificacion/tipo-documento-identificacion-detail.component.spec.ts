import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TramitesAppTestModule } from '../../../test.module';
import { TipoDocumentoIdentificacionDetailComponent } from 'app/entities/tipo-documento-identificacion/tipo-documento-identificacion-detail.component';
import { TipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';

describe('Component Tests', () => {
  describe('TipoDocumentoIdentificacion Management Detail Component', () => {
    let comp: TipoDocumentoIdentificacionDetailComponent;
    let fixture: ComponentFixture<TipoDocumentoIdentificacionDetailComponent>;
    const route = ({ data: of({ tipoDocumentoIdentificacion: new TipoDocumentoIdentificacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [TipoDocumentoIdentificacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipoDocumentoIdentificacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoDocumentoIdentificacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoDocumentoIdentificacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoDocumentoIdentificacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
