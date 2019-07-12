import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Calibration } from 'app/shared/model/calibration.model';
import { CalibrationService } from './calibration.service';
import { CalibrationComponent } from './calibration.component';
import { CalibrationDetailComponent } from './calibration-detail.component';
import { CalibrationUpdateComponent } from './calibration-update.component';
import { CalibrationDeletePopupComponent } from './calibration-delete-dialog.component';
import { ICalibration } from 'app/shared/model/calibration.model';

@Injectable({ providedIn: 'root' })
export class CalibrationResolve implements Resolve<ICalibration> {
  constructor(private service: CalibrationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICalibration> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Calibration>) => response.ok),
        map((calibration: HttpResponse<Calibration>) => calibration.body)
      );
    }
    return of(new Calibration());
  }
}

export const calibrationRoute: Routes = [
  {
    path: '',
    component: CalibrationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Calibrations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CalibrationDetailComponent,
    resolve: {
      calibration: CalibrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Calibrations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CalibrationUpdateComponent,
    resolve: {
      calibration: CalibrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Calibrations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CalibrationUpdateComponent,
    resolve: {
      calibration: CalibrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Calibrations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const calibrationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CalibrationDeletePopupComponent,
    resolve: {
      calibration: CalibrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Calibrations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
