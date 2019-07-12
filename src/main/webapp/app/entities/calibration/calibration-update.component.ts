import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICalibration, Calibration } from 'app/shared/model/calibration.model';
import { CalibrationService } from './calibration.service';

@Component({
  selector: 'jhi-calibration-update',
  templateUrl: './calibration-update.component.html'
})
export class CalibrationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    regionFile: []
  });

  constructor(protected calibrationService: CalibrationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ calibration }) => {
      this.updateForm(calibration);
    });
  }

  updateForm(calibration: ICalibration) {
    this.editForm.patchValue({
      id: calibration.id,
      name: calibration.name,
      regionFile: calibration.regionFile
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const calibration = this.createFromForm();
    if (calibration.id !== undefined) {
      this.subscribeToSaveResponse(this.calibrationService.update(calibration));
    } else {
      this.subscribeToSaveResponse(this.calibrationService.create(calibration));
    }
  }

  private createFromForm(): ICalibration {
    const entity = {
      ...new Calibration(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      regionFile: this.editForm.get(['regionFile']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalibration>>) {
    result.subscribe((res: HttpResponse<ICalibration>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
