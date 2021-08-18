import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TramitesAppTestModule } from '../../../test.module';
import { NivelSatisfacionUpdateComponent } from 'app/entities/nivel-satisfacion/nivel-satisfacion-update.component';
import { NivelSatisfacionService } from 'app/entities/nivel-satisfacion/nivel-satisfacion.service';
import { NivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';

describe('Component Tests', () => {
  describe('NivelSatisfacion Management Update Component', () => {
    let comp: NivelSatisfacionUpdateComponent;
    let fixture: ComponentFixture<NivelSatisfacionUpdateComponent>;
    let service: NivelSatisfacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [NivelSatisfacionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NivelSatisfacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NivelSatisfacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NivelSatisfacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NivelSatisfacion(123);
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
        const entity = new NivelSatisfacion();
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
