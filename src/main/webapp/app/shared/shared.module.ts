import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { QmcaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [QmcaSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [QmcaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QmcaSharedModule {
  static forRoot() {
    return {
      ngModule: QmcaSharedModule
    };
  }
}
