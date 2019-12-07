/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QmcaTestModule } from '../../../test.module';
import { ClientConfigurationDetailComponent } from 'app/entities/client-configuration/client-configuration-detail.component';
import { ClientConfiguration } from 'app/shared/model/client-configuration.model';

describe('Component Tests', () => {
  describe('ClientConfiguration Management Detail Component', () => {
    let comp: ClientConfigurationDetailComponent;
    let fixture: ComponentFixture<ClientConfigurationDetailComponent>;
    const route = ({ data: of({ clientConfiguration: new ClientConfiguration(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QmcaTestModule],
        declarations: [ClientConfigurationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClientConfigurationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClientConfigurationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.clientConfiguration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
