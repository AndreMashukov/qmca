/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { McaTestModule } from '../../../test.module';
import { CalibrationDetailComponent } from 'app/entities/calibration/calibration-detail.component';
import { Calibration } from 'app/shared/model/calibration.model';

describe('Component Tests', () => {
  describe('Calibration Management Detail Component', () => {
    let comp: CalibrationDetailComponent;
    let fixture: ComponentFixture<CalibrationDetailComponent>;
    const route = ({ data: of({ calibration: new Calibration(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [McaTestModule],
        declarations: [CalibrationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CalibrationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CalibrationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.calibration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
