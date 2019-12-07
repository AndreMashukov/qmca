import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientConfiguration } from 'app/shared/model/client-configuration.model';

@Component({
  selector: 'jhi-client-configuration-detail',
  templateUrl: './client-configuration-detail.component.html'
})
export class ClientConfigurationDetailComponent implements OnInit {
  clientConfiguration: IClientConfiguration;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clientConfiguration }) => {
      this.clientConfiguration = clientConfiguration;
    });
  }

  previousState() {
    window.history.back();
  }
}
