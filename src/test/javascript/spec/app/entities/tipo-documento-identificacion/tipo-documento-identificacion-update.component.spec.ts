import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TramitesAppTestModule } from '../../../test.module';
import { TipoDocumentoIdentificacionUpdateComponent } from 'app/entities/tipo-documento-identificacion/tipo-documento-identificacion-update.component';
import { TipoDocumentoIdentificacionService } from 'app/entities/tipo-documento-identificacion/tipo-documento-identificacion.service';
import { TipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';

describe('Component Tests', () => {
  describe('TipoDocumentoIdentificacion Management Update Component', () => {
    let comp: TipoDocumentoIdentificacionUpdateComponent;
    let fixture: ComponentFixture<TipoDocumentoIdentificacionUpdateComponent>;
    let service: TipoDocumentoIdentificacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [TipoDocumentoIdentificacionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipoDocumentoIdentificacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoDocumentoIdentificacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoDocumentoIdentificacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoDocumentoIdentificacion(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoDocumentoIdentificacion();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
