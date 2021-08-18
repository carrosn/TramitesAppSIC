import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TramitesAppTestModule } from '../../../test.module';
import { EncuestaUpdateComponent } from 'app/entities/encuesta/encuesta-update.component';
import { EncuestaService } from 'app/entities/encuesta/encuesta.service';
import { Encuesta } from 'app/shared/model/encuesta.model';

describe('Component Tests', () => {
  describe('Encuesta Management Update Component', () => {
    let comp: EncuestaUpdateComponent;
    let fixture: ComponentFixture<EncuestaUpdateComponent>;
    let service: EncuestaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [EncuestaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EncuestaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EncuestaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EncuestaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Encuesta(123);
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
        const entity = new Encuesta();
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
