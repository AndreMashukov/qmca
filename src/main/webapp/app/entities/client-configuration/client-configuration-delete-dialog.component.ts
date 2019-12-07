import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClientConfiguration } from 'app/shared/model/client-configuration.model';
import { ClientConfigurationService } from './client-configuration.service';

@Component({
  selector: 'jhi-client-configuration-delete-dialog',
  templateUrl: './client-configuration-delete-dialog.component.html'
})
export class ClientConfigurationDeleteDialogComponent {
  clientConfiguration: IClientConfiguration;

  constructor(
    protected clientConfigurationService: ClientConfigurationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.clientConfigurationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'clientConfigurationListModification',
        content: 'Deleted an clientConfiguration'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-client-configuration-delete-popup',
  template: ''
})
export class ClientConfigurationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clientConfiguration }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClientConfigurationDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.clientConfiguration = clientConfiguration;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/client-configuration', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/client-configuration', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
