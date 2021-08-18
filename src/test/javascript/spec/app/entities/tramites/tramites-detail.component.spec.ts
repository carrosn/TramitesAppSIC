import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TramitesAppTestModule } from '../../../test.module';
import { TramitesDetailComponent } from 'app/entities/tramites/tramites-detail.component';
import { Tramites } from 'app/shared/model/tramites.model';

describe('Component Tests', () => {
  describe('Tramites Management Detail Component', () => {
    let comp: TramitesDetailComponent;
    let fixture: ComponentFixture<TramitesDetailComponent>;
    const route = ({ data: of({ tramites: new Tramites(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [TramitesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TramitesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TramitesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tramites on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tramites).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
