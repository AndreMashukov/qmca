import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QmcaSharedModule } from 'app/shared';
import {
  ClientConfigurationComponent,
  ClientConfigurationDetailComponent,
  ClientConfigurationUpdateComponent,
  ClientConfigurationDeletePopupComponent,
  ClientConfigurationDeleteDialogComponent,
  clientConfigurationRoute,
  clientConfigurationPopupRoute
} from './';

const ENTITY_STATES = [...clientConfigurationRoute, ...clientConfigurationPopupRoute];

@NgModule({
  imports: [QmcaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClientConfigurationComponent,
    ClientConfigurationDetailComponent,
    ClientConfigurationUpdateComponent,
    ClientConfigurationDeleteDialogComponent,
    ClientConfigurationDeletePopupComponent
  ],
  entryComponents: [
    ClientConfigurationComponent,
    ClientConfigurationUpdateComponent,
    ClientConfigurationDeleteDialogComponent,
    ClientConfigurationDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QmcaClientConfigurationModule {}
