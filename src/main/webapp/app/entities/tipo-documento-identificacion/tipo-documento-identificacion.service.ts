import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';

type EntityResponseType = HttpResponse<ITipoDocumentoIdentificacion>;
type EntityArrayResponseType = HttpResponse<ITipoDocumentoIdentificacion[]>;

@Injectable({ providedIn: 'root' })
export class TipoDocumentoIdentificacionService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-documento-identificacions';

  constructor(protected http: HttpClient) {}

  create(tipoDocumentoIdentificacion: ITipoDocumentoIdentificacion): Observable<EntityResponseType> {
    return this.http.post<ITipoDocumentoIdentificacion>(this.resourceUrl, tipoDocumentoIdentificacion, { observe: 'response' });
  }

  update(tipoDocumentoIdentificacion: ITipoDocumentoIdentificacion): Observable<EntityResponseType> {
    return this.http.put<ITipoDocumentoIdentificacion>(this.resourceUrl, tipoDocumentoIdentificacion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoDocumentoIdentificacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoDocumentoIdentificacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
