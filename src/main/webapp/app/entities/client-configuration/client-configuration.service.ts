import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClientConfiguration } from 'app/shared/model/client-configuration.model';

type EntityResponseType = HttpResponse<IClientConfiguration>;
type EntityArrayResponseType = HttpResponse<IClientConfiguration[]>;

@Injectable({ providedIn: 'root' })
export class ClientConfigurationService {
  public resourceUrl = SERVER_API_URL + 'api/client-configurations';

  constructor(protected http: HttpClient) {}

  create(clientConfiguration: IClientConfiguration): Observable<EntityResponseType> {
    return this.http.post<IClientConfiguration>(this.resourceUrl, clientConfiguration, { observe: 'response' });
  }

  update(clientConfiguration: IClientConfiguration): Observable<EntityResponseType> {
    return this.http.put<IClientConfiguration>(this.resourceUrl, clientConfiguration, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClientConfiguration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClientConfiguration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
