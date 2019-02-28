import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

import { QueenBee } from '../classes/queenBee';
import { CrudService } from './crud.service';

@Injectable({
  providedIn: 'root'
})
export class QueenBeesService extends CrudService<QueenBee> {

  constructor(
    private httpClient: HttpClient,
    private activatedRoute: ActivatedRoute) {

    super(httpClient, activatedRoute);
  }
  
  getBaseUrl(): string {

    return `farms/${this.getFarmId()}/hives/${this.getHiveId()}/queenBees`;
  }

  getFarmId(): number {

    return this.getRouteParam('farm_id');
  }

  getHiveId(): number {

    return this.getRouteParam('hive_id');
  }
}
