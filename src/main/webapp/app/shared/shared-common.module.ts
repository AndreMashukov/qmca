import { NgModule } from '@angular/core';

import { QmcaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [QmcaSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [QmcaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class QmcaSharedCommonModule {}
