import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TipoDocumentoIdentificacionService } from 'app/entities/tipo-documento-identificacion/tipo-documento-identificacion.service';
import { ITipoDocumentoIdentificacion, TipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';

describe('Service Tests', () => {
  describe('TipoDocumentoIdentificacion Service', () => {
    let injector: TestBed;
    let service: TipoDocumentoIdentificacionService;
    let httpMock: HttpTestingController;
    let elemDefault: ITipoDocumentoIdentificacion;
    let expectedResult: ITipoDocumentoIdentificacion | ITipoDocumentoIdentificacion[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TipoDocumentoIdentificacionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TipoDocumentoIdentificacion(0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TipoDocumentoIdentificacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TipoDocumentoIdentificacion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TipoDocumentoIdentificacion', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            abreviatura: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TipoDocumentoIdentificacion', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            abreviatura: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TipoDocumentoIdentificacion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
