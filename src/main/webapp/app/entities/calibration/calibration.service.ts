import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICalibration } from 'app/shared/model/calibration.model';

type EntityResponseType = HttpResponse<ICalibration>;
type EntityArrayResponseType = HttpResponse<ICalibration[]>;

@Injectable({ providedIn: 'root' })
export class CalibrationService {
  public resourceUrl = SERVER_API_URL + 'api/calibrations';

  constructor(protected http: HttpClient) {}

  create(calibration: ICalibration): Observable<EntityResponseType> {
    return this.http.post<ICalibration>(this.resourceUrl, calibration, { observe: 'response' });
  }

  update(calibration: ICalibration): Observable<EntityResponseType> {
    return this.http.put<ICalibration>(this.resourceUrl, calibration, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICalibration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICalibration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
