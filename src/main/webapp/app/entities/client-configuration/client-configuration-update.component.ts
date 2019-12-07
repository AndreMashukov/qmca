import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IClientConfiguration, ClientConfiguration } from 'app/shared/model/client-configuration.model';
import { ClientConfigurationService } from './client-configuration.service';

@Component({
  selector: 'jhi-client-configuration-update',
  templateUrl: './client-configuration-update.component.html'
})
export class ClientConfigurationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    clientId: [],
    attachment: [],
    preTableNotificationTemplate: [null, [Validators.required]],
    postTableTemplate: [null, [Validators.required]]
  });

  constructor(
    protected clientConfigurationService: ClientConfigurationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ clientConfiguration }) => {
      this.updateForm(clientConfiguration);
    });
  }

  updateForm(clientConfiguration: IClientConfiguration) {
    this.editForm.patchValue({
      id: clientConfiguration.id,
      clientId: clientConfiguration.clientId,
      attachment: clientConfiguration.attachment,
      preTableNotificationTemplate: clientConfiguration.preTableNotificationTemplate,
      postTableTemplate: clientConfiguration.postTableTemplate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const clientConfiguration = this.createFromForm();
    if (clientConfiguration.id !== undefined) {
      this.subscribeToSaveResponse(this.clientConfigurationService.update(clientConfiguration));
    } else {
      this.subscribeToSaveResponse(this.clientConfigurationService.create(clientConfiguration));
    }
  }

  private createFromForm(): IClientConfiguration {
    const entity = {
      ...new ClientConfiguration(),
      id: this.editForm.get(['id']).value,
      clientId: this.editForm.get(['clientId']).value,
      attachment: this.editForm.get(['attachment']).value,
      preTableNotificationTemplate: this.editForm.get(['preTableNotificationTemplate']).value,
      postTableTemplate: this.editForm.get(['postTableTemplate']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClientConfiguration>>) {
    result.subscribe((res: HttpResponse<IClientConfiguration>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
