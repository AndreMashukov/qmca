import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { McaSharedModule } from 'app/shared';
import {
  CalibrationComponent,
  CalibrationDetailComponent,
  CalibrationUpdateComponent,
  CalibrationDeletePopupComponent,
  CalibrationDeleteDialogComponent,
  calibrationRoute,
  calibrationPopupRoute
} from './';

const ENTITY_STATES = [...calibrationRoute, ...calibrationPopupRoute];

@NgModule({
  imports: [McaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CalibrationComponent,
    CalibrationDetailComponent,
    CalibrationUpdateComponent,
    CalibrationDeleteDialogComponent,
    CalibrationDeletePopupComponent
  ],
  entryComponents: [CalibrationComponent, CalibrationUpdateComponent, CalibrationDeleteDialogComponent, CalibrationDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class McaCalibrationModule {}
