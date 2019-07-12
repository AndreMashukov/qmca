import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { McaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [McaSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [McaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class McaSharedModule {
  static forRoot() {
    return {
      ngModule: McaSharedModule
    };
  }
}
