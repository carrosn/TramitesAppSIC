import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoPersona } from 'app/shared/model/tipo-persona.model';

type EntityResponseType = HttpResponse<ITipoPersona>;
type EntityArrayResponseType = HttpResponse<ITipoPersona[]>;

@Injectable({ providedIn: 'root' })
export class TipoPersonaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-personas';

  constructor(protected http: HttpClient) {}

  create(tipoPersona: ITipoPersona): Observable<EntityResponseType> {
    return this.http.post<ITipoPersona>(this.resourceUrl, tipoPersona, { observe: 'response' });
  }

  update(tipoPersona: ITipoPersona): Observable<EntityResponseType> {
    return this.http.put<ITipoPersona>(this.resourceUrl, tipoPersona, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoPersona>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoPersona[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
