import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalibration } from 'app/shared/model/calibration.model';

@Component({
  selector: 'jhi-calibration-detail',
  templateUrl: './calibration-detail.component.html'
})
export class CalibrationDetailComponent implements OnInit {
  calibration: ICalibration;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ calibration }) => {
      this.calibration = calibration;
    });
  }

  previousState() {
    window.history.back();
  }
}
