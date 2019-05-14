import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AgamottoSharedLibsModule, AgamottoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [AgamottoSharedLibsModule, AgamottoSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [AgamottoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgamottoSharedModule {
  static forRoot() {
    return {
      ngModule: AgamottoSharedModule
    };
  }
}
