/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { QmcaTestModule } from '../../../test.module';
import { ClientConfigurationDeleteDialogComponent } from 'app/entities/client-configuration/client-configuration-delete-dialog.component';
import { ClientConfigurationService } from 'app/entities/client-configuration/client-configuration.service';

describe('Component Tests', () => {
  describe('ClientConfiguration Management Delete Component', () => {
    let comp: ClientConfigurationDeleteDialogComponent;
    let fixture: ComponentFixture<ClientConfigurationDeleteDialogComponent>;
    let service: ClientConfigurationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QmcaTestModule],
        declarations: [ClientConfigurationDeleteDialogComponent]
      })
        .overrideTemplate(ClientConfigurationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClientConfigurationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClientConfigurationService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
