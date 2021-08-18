import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TramitesAppTestModule } from '../../../test.module';
import { TipoPersonaDetailComponent } from 'app/entities/tipo-persona/tipo-persona-detail.component';
import { TipoPersona } from 'app/shared/model/tipo-persona.model';

describe('Component Tests', () => {
  describe('TipoPersona Management Detail Component', () => {
    let comp: TipoPersonaDetailComponent;
    let fixture: ComponentFixture<TipoPersonaDetailComponent>;
    const route = ({ data: of({ tipoPersona: new TipoPersona(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TramitesAppTestModule],
        declarations: [TipoPersonaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipoPersonaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoPersonaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoPersona on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoPersona).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
