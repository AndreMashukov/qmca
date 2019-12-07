import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ClientConfiguration } from 'app/shared/model/client-configuration.model';
import { ClientConfigurationService } from './client-configuration.service';
import { ClientConfigurationComponent } from './client-configuration.component';
import { ClientConfigurationDetailComponent } from './client-configuration-detail.component';
import { ClientConfigurationUpdateComponent } from './client-configuration-update.component';
import { ClientConfigurationDeletePopupComponent } from './client-configuration-delete-dialog.component';
import { IClientConfiguration } from 'app/shared/model/client-configuration.model';

@Injectable({ providedIn: 'root' })
export class ClientConfigurationResolve implements Resolve<IClientConfiguration> {
  constructor(private service: ClientConfigurationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClientConfiguration> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ClientConfiguration>) => response.ok),
        map((clientConfiguration: HttpResponse<ClientConfiguration>) => clientConfiguration.body)
      );
    }
    return of(new ClientConfiguration());
  }
}

export const clientConfigurationRoute: Routes = [
  {
    path: '',
    component: ClientConfigurationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ClientConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClientConfigurationDetailComponent,
    resolve: {
      clientConfiguration: ClientConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ClientConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClientConfigurationUpdateComponent,
    resolve: {
      clientConfiguration: ClientConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ClientConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClientConfigurationUpdateComponent,
    resolve: {
      clientConfiguration: ClientConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ClientConfigurations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const clientConfigurationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ClientConfigurationDeletePopupComponent,
    resolve: {
      clientConfiguration: ClientConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ClientConfigurations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
