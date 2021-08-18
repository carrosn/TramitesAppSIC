import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';

type EntityResponseType = HttpResponse<INivelSatisfacion>;
type EntityArrayResponseType = HttpResponse<INivelSatisfacion[]>;

@Injectable({ providedIn: 'root' })
export class NivelSatisfacionService {
  public resourceUrl = SERVER_API_URL + 'api/nivel-satisfacions';

  constructor(protected http: HttpClient) {}

  create(nivelSatisfacion: INivelSatisfacion): Observable<EntityResponseType> {
    return this.http.post<INivelSatisfacion>(this.resourceUrl, nivelSatisfacion, { observe: 'response' });
  }

  update(nivelSatisfacion: INivelSatisfacion): Observable<EntityResponseType> {
    return this.http.put<INivelSatisfacion>(this.resourceUrl, nivelSatisfacion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INivelSatisfacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INivelSatisfacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
