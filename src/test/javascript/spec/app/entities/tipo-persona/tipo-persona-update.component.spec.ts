import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TramitesAppTestModule } from '../../../test.module';
import { TipoPersonaUpdateComponent } from 'app/entities/tipo-persona/tipo-persona-update.component';
import { TipoPersonaService } from 'app/entities/tipo-persona/tipo-persona.service';
import { TipoPersona } from 'app/shared/model/tipo-persona.model';

describe('Component Tests', () => {
  describe('TipoPersona Management Update Component', () => {
    let comp: TipoPersonaUpdateComponent;
    let fixture: ComponentFixture<TipoPersonaUpdateComponent>;
    let service: TipoPersonaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [TipoPersonaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipoPersonaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoPersonaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoPersonaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoPersona(123);
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
        const entity = new TipoPersona();
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
