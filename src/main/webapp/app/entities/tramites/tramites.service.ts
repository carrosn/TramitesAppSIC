import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITramites } from 'app/shared/model/tramites.model';

type EntityResponseType = HttpResponse<ITramites>;
type EntityArrayResponseType = HttpResponse<ITramites[]>;

@Injectable({ providedIn: 'root' })
export class TramitesService {
  public resourceUrl = SERVER_API_URL + 'api/tramites';

  constructor(protected http: HttpClient) {}

  create(tramites: ITramites): Observable<EntityResponseType> {
    return this.http.post<ITramites>(this.resourceUrl, tramites, { observe: 'response' });
  }

  update(tramites: ITramites): Observable<EntityResponseType> {
    return this.http.put<ITramites>(this.resourceUrl, tramites, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITramites>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITramites[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
