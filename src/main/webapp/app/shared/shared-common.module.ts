import { NgModule } from '@angular/core';

import { McaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [McaSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [McaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class McaSharedCommonModule {}
