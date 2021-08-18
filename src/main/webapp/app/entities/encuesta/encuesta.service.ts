import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEncuesta } from 'app/shared/model/encuesta.model';

type EntityResponseType = HttpResponse<IEncuesta>;
type EntityArrayResponseType = HttpResponse<IEncuesta[]>;

@Injectable({ providedIn: 'root' })
export class EncuestaService {
  public resourceUrl = SERVER_API_URL + 'api/encuestas';

  constructor(protected http: HttpClient) {}

  create(encuesta: IEncuesta): Observable<EntityResponseType> {
    return this.http.post<IEncuesta>(this.resourceUrl, encuesta, { observe: 'response' });
  }

  update(encuesta: IEncuesta): Observable<EntityResponseType> {
    return this.http.put<IEncuesta>(this.resourceUrl, encuesta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEncuesta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEncuesta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
