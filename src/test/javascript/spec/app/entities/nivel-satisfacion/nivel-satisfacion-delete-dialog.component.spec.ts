import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TramitesAppTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { NivelSatisfacionDeleteDialogComponent } from 'app/entities/nivel-satisfacion/nivel-satisfacion-delete-dialog.component';
import { NivelSatisfacionService } from 'app/entities/nivel-satisfacion/nivel-satisfacion.service';

describe('Component Tests', () => {
  describe('NivelSatisfacion Management Delete Component', () => {
    let comp: NivelSatisfacionDeleteDialogComponent;
    let fixture: ComponentFixture<NivelSatisfacionDeleteDialogComponent>;
    let service: NivelSatisfacionService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [NivelSatisfacionDeleteDialogComponent],
      })
        .overrideTemplate(NivelSatisfacionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NivelSatisfacionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NivelSatisfacionService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
