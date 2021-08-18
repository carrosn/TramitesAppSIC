import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TramitesAppTestModule } from '../../../test.module';
import { NivelSatisfacionDetailComponent } from 'app/entities/nivel-satisfacion/nivel-satisfacion-detail.component';
import { NivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';

describe('Component Tests', () => {
  describe('NivelSatisfacion Management Detail Component', () => {
    let comp: NivelSatisfacionDetailComponent;
    let fixture: ComponentFixture<NivelSatisfacionDetailComponent>;
    const route = ({ data: of({ nivelSatisfacion: new NivelSatisfacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [NivelSatisfacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NivelSatisfacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NivelSatisfacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nivelSatisfacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nivelSatisfacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
