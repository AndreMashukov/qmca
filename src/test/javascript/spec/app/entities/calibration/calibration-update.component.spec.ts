/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { McaTestModule } from '../../../test.module';
import { CalibrationUpdateComponent } from 'app/entities/calibration/calibration-update.component';
import { CalibrationService } from 'app/entities/calibration/calibration.service';
import { Calibration } from 'app/shared/model/calibration.model';

describe('Component Tests', () => {
  describe('Calibration Management Update Component', () => {
    let comp: CalibrationUpdateComponent;
    let fixture: ComponentFixture<CalibrationUpdateComponent>;
    let service: CalibrationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [McaTestModule],
        declarations: [CalibrationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CalibrationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalibrationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalibrationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Calibration(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Calibration();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
