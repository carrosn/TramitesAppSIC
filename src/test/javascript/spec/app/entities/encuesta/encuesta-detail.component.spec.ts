import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TramitesAppTestModule } from '../../../test.module';
import { EncuestaDetailComponent } from 'app/entities/encuesta/encuesta-detail.component';
import { Encuesta } from 'app/shared/model/encuesta.model';

describe('Component Tests', () => {
  describe('Encuesta Management Detail Component', () => {
    let comp: EncuestaDetailComponent;
    let fixture: ComponentFixture<EncuestaDetailComponent>;
    const route = ({ data: of({ encuesta: new Encuesta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [EncuestaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EncuestaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EncuestaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load encuesta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.encuesta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
