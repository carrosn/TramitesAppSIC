import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TramitesAppTestModule } from '../../../test.module';
import { TramitesUpdateComponent } from 'app/entities/tramites/tramites-update.component';
import { TramitesService } from 'app/entities/tramites/tramites.service';
import { Tramites } from 'app/shared/model/tramites.model';

describe('Component Tests', () => {
  describe('Tramites Management Update Component', () => {
    let comp: TramitesUpdateComponent;
    let fixture: ComponentFixture<TramitesUpdateComponent>;
    let service: TramitesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [TramitesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TramitesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TramitesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TramitesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tramites(123);
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
        const entity = new Tramites();
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
